/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启程促销系统启动类
 *
 * @author wuguocheng
 */
@SpringBootApplication
@MapperScan("com.gcnbl.dao")
@EnableScheduling
public class QiChengApplication {

    public static void main(String[] args) {
        SpringApplication.run(QiChengApplication.class, args);
        System.out.println("========================================");
        System.out.println("   脐橙促销系统启动成功！");
        System.out.println("   访问地址: http://localhost:8080/qicheng_promotion");
        System.out.println("========================================");
    }
}
