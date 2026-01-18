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
 * @data 2025/12/21 21:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAuthenticationDO {
    private Long userId;

    private String realName;

    private String idCardNumber;

    private Integer authenticationStatus;

    private String roleType;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;
}
