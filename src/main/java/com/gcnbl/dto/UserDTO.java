/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.dto;

import lombok.Data;

/**
 * @author wuguocheng
 * @data 2025/12/21 21:41
 */
@Data
public class UserDTO {
    private Long userId;
    private String phoneNumber;
    private String realName;
    private String idCardNumber;
    private String roleType;
    private String authenticationStatus;
}
