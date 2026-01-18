/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.controller;

import com.gcnbl.dto.ResponseDTO;
import com.gcnbl.dto.UserDTO;
import com.gcnbl.query.*;
import com.gcnbl.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuguocheng
 * @data 2025/12/21 18:04
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     * @param query 发送验证码请求参数
     * @return 响应结果
     */
    @PostMapping("/sendVerificationCode")
    public ResponseDTO sendVerificationCode(@Valid @RequestBody SendVerificationCodeQuery query) {
        userService.sendVerificationCode(query.getPhoneNumber());
        return new ResponseDTO("000000", "验证码发送成功");
    }

    /**
     * 手机号验证码登录
     * @param query 登录请求参数
     * @return 响应结果
     */
    @PostMapping("/login")
    public ResponseDTO login(@Valid @RequestBody LoginQuery query) {
        UserDTO userDTO = userService.login(query.getPhoneNumber(), query.getVerificationCode());
        return new ResponseDTO("000000", "登录成功", userDTO);
    }

    /**
     * 用户实名认证
     * @param query 认证请求参数
     * @return 响应结果
     */
    @PostMapping("/authenticate")
    public ResponseDTO authenticate(@Valid @RequestBody AuthenticationQuery query) {
        try {
            userService.authenticate(query.getUserId(), query.getRealName(), query.getIdCardNumber(), query.getRoleType());
            return new ResponseDTO("000000", "认证申请提交成功");
        } catch (IllegalArgumentException e) {
            log.error("用户信息不存在或身份证号码已被认证: {}", e.getMessage());
            return new ResponseDTO("000001", "用户信息不存在或身份证号码已被认证");
        } catch (Exception e) {
            log.error("身份证号码已被认证: {}", e.getMessage());
            return new ResponseDTO("000005", "身份证号码已被认证");
        }
    }

    /**
     * 查询用户认证状态
     * @param userId 用户ID
     * @return 响应结果
     */
    @GetMapping("/authenticationStatus/{userId}")
    public ResponseDTO getAuthenticationStatus(@PathVariable Long userId) {
        try {
            String status = userService.getAuthenticationStatus(userId);
            return new ResponseDTO("000000", "查询成功", status);
        } catch (IllegalArgumentException e) {
            log.error("用户信息不存在: {}", e.getMessage());
            return new ResponseDTO("000001", "用户信息不存在");
        }
    }

    /**
     * 设置用户角色
     * @param query 设置用户角色请求参数
     * @return 响应结果
     */
    @PostMapping("/setUserRole")
    public ResponseDTO setUserRole(@Valid @RequestBody SetUserRoleQuery query) {
        try {
            userService.setUserRole(query.getUserId(), query.getRoleType());
            return new ResponseDTO("000000", "设置角色成功");
        } catch (IllegalArgumentException e) {
            return new ResponseDTO("000001", e.getMessage());
        } catch (IllegalStateException e) {
            return new ResponseDTO("000002", e.getMessage());
        }
    }

    /**
     * 获取用户角色
     * @param query 获取用户角色请求参数
     * @return 响应结果
     */
    @PostMapping("/getUserRole")
    public ResponseDTO getUserRole(@Valid @RequestBody GetUserRoleQuery query) {
        try {
            String roleType = userService.getUserRole(query.getUserId());
            return new ResponseDTO("000000", "获取角色成功", roleType);
        } catch (IllegalArgumentException e) {
            return new ResponseDTO("000001", e.getMessage());
        }
    }

    /**
     * 转换用户角色
     * @param query 转换用户角色请求参数
     * @return 响应结果
     */
    @PostMapping("/convertUserRole")
    public ResponseDTO convertUserRole(@Valid @RequestBody ConvertUserRoleQuery query) {
        try {
            userService.convertUserRole(query.getUserId(), query.getNewRoleType());
            return new ResponseDTO("000000", "转换角色成功");
        } catch (IllegalArgumentException e) {
            return new ResponseDTO("000001", e.getMessage());
        } catch (IllegalStateException e) {
            return new ResponseDTO("000002", e.getMessage());
        } catch (UnsupportedOperationException e) {
            return new ResponseDTO("000003", e.getMessage());
        }
    }

    /**
     * 列出所有用户及其角色
     * @return 响应结果
     */
    @GetMapping("/listAllUsersWithRoles")
    public ResponseDTO listAllUsersWithRoles() {
        List<UserDTO> users = userService.listAllUsersWithRoles();
        if (users.isEmpty()) {
            return new ResponseDTO("000004", "没有用户");
        }
        return new ResponseDTO("000000", "调用成功", users);
    }

}
