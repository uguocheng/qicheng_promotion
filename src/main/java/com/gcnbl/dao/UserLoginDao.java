/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.dao;

import com.gcnbl.entity.UserLoginDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户登录数据访问接口
 *
 * @author wuguocheng
 */
@Mapper
public interface UserLoginDao {

    /**
     * 根据手机号查询用户登录信息
     *
     * @param phoneNumber 手机号
     * @return 用户登录信息
     */
    UserLoginDO findByPhoneNumber(String phoneNumber);

    /**
     * 根据OpenID查询用户登录信息
     *
     * @param openId OpenID
     * @return 用户登录信息
     */
    UserLoginDO findByOpenId(String openId);

    /**
     * 根据用户ID查询用户登录信息
     *
     * @param userId 用户ID
     * @return 用户登录信息
     */
    UserLoginDO findById(Long userId);

    /**
     * 更新用户登录信息
     *
     * @param userLoginDO 用户登录信息
     */
    void update(UserLoginDO userLoginDO);

    /**
     * 插入用户登录信息
     *
     * @param userLoginDO 用户登录信息
     * @return 影响行数
     */
    int insert(UserLoginDO userLoginDO);
}
