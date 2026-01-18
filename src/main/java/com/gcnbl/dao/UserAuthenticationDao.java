/*
 * 小吴公司拥有本软件版权2025并保留所有权利。
 * Copyright 2025, Penglai.com,xiaowu Co.,Ltd,
 * All rights reserved.
 */

package com.gcnbl.dao;

import com.gcnbl.entity.UserAuthenticationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户认证信息数据访问接口
 *
 * @author wuguocheng
 */
@Mapper
public interface UserAuthenticationDao {

    /**
     * 根据用户ID查询用户认证信息
     *
     * @param userId 用户ID
     * @return 用户认证信息
     */
    UserAuthenticationDO findByUserId(@Param("userId") Long userId);

    /**
     * 根据身份证号码查询用户认证信息
     *
     * @param idCardNumber 身份证号码
     * @return 用户认证信息
     */
    UserAuthenticationDO findByIdCardNumber(@Param("idCardNumber") String idCardNumber);

    /**
     * 根据用户ID查找用户认证信息
     *
     * @param userId 用户ID
     * @return 用户认证信息
     */
    UserAuthenticationDO findById(@Param("userId") Long userId);

    /**
     * 插入用户认证信息
     *
     * @param userAuthenticationDO 用户认证信息
     * @return 影响行数
     */
    int insert(UserAuthenticationDO userAuthenticationDO);

    /**
     * 更新用户认证信息
     *
     * @param userAuthenticationDO 用户认证信息
     */
    void update(UserAuthenticationDO userAuthenticationDO);

    /**
     * 查询所有用户认证信息
     *
     * @return 用户认证信息列表
     */
    List<UserAuthenticationDO> findAll();
}
