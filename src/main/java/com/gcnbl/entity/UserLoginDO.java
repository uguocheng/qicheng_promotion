/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wuguocheng
 * @data 2025/12/21 21:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDO {
    private Long userId;
    private String phoneNumber;
    private String passwordHash;
    private String verificationCode;
    private LocalDateTime verificationCodeExpiry;
    
    // 微信登录相关字段
    private String openId;
    private String roleType;
    private Integer authenticationStatus;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String language;
    private String city;
    
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;
}
