/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.service.impl;

import com.gcnbl.dao.UserAuthenticationDao;
import com.gcnbl.dao.UserLoginDao;
import com.gcnbl.dto.UserDTO;
import com.gcnbl.entity.UserAuthenticationDO;
import com.gcnbl.entity.UserLoginDO;
import com.gcnbl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author penglai
 * @data 2025/12/21 18:05
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLoginDao userLoginDao;

    @Autowired
    private UserAuthenticationDao userAuthenticationDao;

    /**
     * 发送验证码
     * @param phoneNumber 用户电话
     */
    @Override
    public void sendVerificationCode(String phoneNumber) {
        UserLoginDO userLoginDO = userLoginDao.findByPhoneNumber(phoneNumber);
        if (userLoginDO == null) {
            throw new IllegalArgumentException("用户信息不存在");
        }
        String verificationCode = generateVerificationCode();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        userLoginDO.setVerificationCode(verificationCode);
        userLoginDO.setVerificationCodeExpiry(expiryTime);
        userLoginDao.update(userLoginDO);
    }

    /**
     * 手机号验证码登录
     * @param phoneNumber 用户电话
     * @param verificationCode 验证码
     * @return 用户信息
     */
    @Override
    public UserDTO login(String phoneNumber, String verificationCode) {
        UserLoginDO userLoginDO = userLoginDao.findByPhoneNumber(phoneNumber);
        if (userLoginDO == null) {
            throw new IllegalArgumentException("用户信息不存在");
        }
        if (!userLoginDO.getVerificationCode().equals(verificationCode) || userLoginDO.getVerificationCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("验证码错误或已过期");
        }
        UserAuthenticationDO userAuthenticationDO = userAuthenticationDao.findByUserId(userLoginDO.getUserId());
        if (userAuthenticationDO == null) {
            throw new IllegalArgumentException("用户认证信息不存在");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userAuthenticationDO.getUserId());
        userDTO.setPhoneNumber(userLoginDO.getPhoneNumber());
        userDTO.setRealName(userAuthenticationDO.getRealName());
        userDTO.setIdCardNumber(userAuthenticationDO.getIdCardNumber());
        userDTO.setRoleType(userAuthenticationDO.getRoleType());
        userDTO.setAuthenticationStatus(String.valueOf(userAuthenticationDO.getAuthenticationStatus()));
        return userDTO;
    }

    /**
     * 用户实名认证
     * @param userId 用户ID
     * @param realName 真实姓名
     * @param idCardNumber 身份证号码
     * @param roleType 角色类型
     */
    @Override
    @Transactional
    public void authenticate(Long userId, String realName, String idCardNumber, String roleType) {
        UserAuthenticationDO existingAuth = userAuthenticationDao.findByIdCardNumber(idCardNumber);
        if (existingAuth != null && existingAuth.getAuthenticationStatus() == 2) {
            throw new IllegalArgumentException("身份证号码已被认证");
        }
        UserLoginDO userLoginDO = userLoginDao.findById(userId);
        if (userLoginDO == null) {
            throw new IllegalArgumentException("用户信息不存在");
        }
        UserAuthenticationDO userAuthenticationDO = new UserAuthenticationDO();
        userAuthenticationDO.setUserId(userId);
        userAuthenticationDO.setRealName(realName);
        userAuthenticationDO.setIdCardNumber(idCardNumber);
        userAuthenticationDO.setRoleType(roleType);
        // 认证中
        userAuthenticationDO.setAuthenticationStatus( 1);
        userAuthenticationDO.setCreateBy("system");
        userAuthenticationDO.setUpdateBy("system");
        userAuthenticationDao.insert(userAuthenticationDO);
    }

    /**
     * 查询用户认证状态
     * @param userId 用户ID
     * @return 认证状态
     */
    @Override
    public String getAuthenticationStatus(Long userId) {
        UserAuthenticationDO userAuthenticationDO = userAuthenticationDao.findByUserId(userId);
        if (userAuthenticationDO == null) {
            throw new IllegalArgumentException("用户信息不存在");
        }
        return String.valueOf(userAuthenticationDO.getAuthenticationStatus());
    }

    /**
     * 生成6位随机验证码
     * @return 验证码
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    /**
     * 设置用户角色
     * @param userId 用户ID
     * @param roleType 角色类型
     */
    @Override
    @Transactional
    public void setUserRole(Long userId, String roleType) {
        UserAuthenticationDO userAuthenticationDO = userAuthenticationDao.findByUserId(userId);
        if (userAuthenticationDO == null) {
            throw new IllegalArgumentException("用户信息不存在");
        }
        if (!isValidRoleType(roleType)) {
            throw new IllegalStateException("无效的角色类型");
        }
        userAuthenticationDO.setRoleType(roleType);
        userAuthenticationDO.setUpdateBy("system");
        userAuthenticationDao.update(userAuthenticationDO);
    }

    /**
     * 获取用户角色
     * @param userId 用户ID
     * @return 角色类型
     */
    @Override
    public String getUserRole(Long userId) {
        UserAuthenticationDO userAuthenticationDO = userAuthenticationDao.findByUserId(userId);
        if (userAuthenticationDO == null) {
            throw new IllegalArgumentException("用户信息不存在");
        }
        return userAuthenticationDO.getRoleType();
    }

    /**
     * 转换用户角色
     * @param userId 用户ID
     * @param newRoleType 新角色类型
     */
    @Override
    @Transactional
    public void convertUserRole(Long userId, String newRoleType) {
        UserAuthenticationDO userAuthenticationDO = userAuthenticationDao.findByUserId(userId);
        if (userAuthenticationDO == null) {
            throw new IllegalArgumentException("用户信息不存在");
        }
        if (!isValidRoleType(newRoleType)) {
            throw new IllegalStateException("无效的新角色类型");
        }
        String currentRoleType = userAuthenticationDO.getRoleType();
        if (!canConvertRole(currentRoleType, newRoleType)) {
            throw new UnsupportedOperationException("无法转换为目标角色");
        }
        userAuthenticationDO.setRoleType(newRoleType);
        userAuthenticationDO.setUpdateBy("system");
        userAuthenticationDao.update(userAuthenticationDO);
    }

    /**
     * 列出所有用户及其角色
     * @return 用户信息列表
     */
    @Override
    public List<UserDTO> listAllUsersWithRoles() {
        List<UserAuthenticationDO> userAuthenticationDOS = userAuthenticationDao.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (UserAuthenticationDO userAuthenticationDO : userAuthenticationDOS) {
            UserLoginDO userLoginDO = userLoginDao.findById(userAuthenticationDO.getUserId());
            if (userLoginDO != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(userAuthenticationDO.getUserId());
                userDTO.setPhoneNumber(userLoginDO.getPhoneNumber());
                userDTO.setRealName(userAuthenticationDO.getRealName());
                userDTO.setIdCardNumber(userAuthenticationDO.getIdCardNumber());
                userDTO.setRoleType(userAuthenticationDO.getRoleType());
                userDTO.setAuthenticationStatus(String.valueOf(userAuthenticationDO.getAuthenticationStatus()));
                userDTOS.add(userDTO);
            }
        }
        return userDTOS;
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserDTO getUser(Long userId) {
        UserAuthenticationDO userAuthenticationDO = userAuthenticationDao.findByUserId(userId);
        if (userAuthenticationDO == null) {
            return null;
        }
        UserLoginDO userLoginDO = userLoginDao.findById(userId);
        if (userLoginDO == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userAuthenticationDO.getUserId());
        userDTO.setPhoneNumber(userLoginDO.getPhoneNumber());
        userDTO.setRealName(userAuthenticationDO.getRealName());
        userDTO.setIdCardNumber(userAuthenticationDO.getIdCardNumber());
        userDTO.setRoleType(userAuthenticationDO.getRoleType());
        userDTO.setAuthenticationStatus(String.valueOf(userAuthenticationDO.getAuthenticationStatus()));
        return userDTO;
    }

    /**
     * 验证角色类型是否有效
     * @param roleType 角色类型
     * @return 是否有效
     */
    private boolean isValidRoleType(String roleType) {
        return "admin".equals(roleType) || "pusher".equals(roleType) || "normal".equals(roleType);
    }

    /**
     * 检查当前角色是否可以转换为目标角色
     * @param currentRoleType 当前角色类型
     * @param newRoleType 新角色类型
     * @return 是否可以转换
     */
    private boolean canConvertRole(String currentRoleType, String newRoleType) {
        // 这里可以根据具体业务逻辑来实现角色转换规则
        return !currentRoleType.equals(newRoleType);
    }
}
