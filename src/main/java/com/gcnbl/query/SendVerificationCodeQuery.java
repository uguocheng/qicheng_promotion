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
 * @data 2025/12/21 22:09
 */
@Data
public class SendVerificationCodeQuery {
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;
}
