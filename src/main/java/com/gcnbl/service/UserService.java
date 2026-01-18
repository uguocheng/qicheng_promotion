/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.service;

import com.gcnbl.dto.UserDTO;

import java.util.List;

/**
 * @author penglai
 * @data 2025/12/21 18:05
 */
public interface UserService {

    /**
     * 发送验证码
     * @param phoneNumber 用户电话
     */
    void sendVerificationCode(String phoneNumber);

    /**
     * 手机号验证码登录
     * @param phoneNumber 用户电话
     * @param verificationCode 验证码
     * @return 用户信息
     */
    UserDTO login(String phoneNumber, String verificationCode);

    /**
     * 用户实名认证
     * @param userId 用户ID
     * @param realName 真实姓名
     * @param idCardNumber 身份证号码
     * @param roleType 角色类型
     */
    void authenticate(Long userId, String realName, String idCardNumber, String roleType);

    /**
     * 查询用户认证状态
     * @param userId 用户ID
     * @return 认证状态
     */
    String getAuthenticationStatus(Long userId);

    /**
     * 设置用户角色
     * @param userId 用户ID
     * @param roleType 角色类型
     */
    void setUserRole(Long userId, String roleType);

    /**
     * 获取用户角色
     * @param userId 用户ID
     * @return 角色类型
     */
    String getUserRole(Long userId);

    /**
     * 转换用户角色
     * @param userId 用户ID
     * @param newRoleType 新角色类型
     */
    void convertUserRole(Long userId, String newRoleType);

    /**
     * 列出所有用户及其角色
     * @return 用户信息列表
     */
    List<UserDTO> listAllUsersWithRoles();

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    UserDTO getUser(Long userId);
}
