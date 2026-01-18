/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.query;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wuguocheng
 * @data 2025/12/21 22:12
 */
@Data
public class GetUserRoleQuery {
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
