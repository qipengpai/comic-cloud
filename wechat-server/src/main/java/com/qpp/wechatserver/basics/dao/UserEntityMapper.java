package com.qpp.wechatserver.basics.dao;

import com.qpp.wechatserver.basics.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityMapper {
    int deleteByPrimaryKey(String userid);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}