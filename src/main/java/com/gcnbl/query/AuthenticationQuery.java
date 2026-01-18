/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.query;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author wuguocheng
 * @data 2025/12/21 22:10
 */
@Data
public class AuthenticationQuery {
    private Long userId;

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "身份证号码不能为空")
    private String idCardNumber;

    @NotBlank(message = "角色类型不能为空")
    private String roleType;
}
