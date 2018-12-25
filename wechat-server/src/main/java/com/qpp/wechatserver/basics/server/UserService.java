package com.qpp.wechatserver.basics.server;

import com.qpp.wechatserver.basics.dao.UserEntityMapper;
import com.qpp.wechatserver.basics.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    public UserEntity get(String userId) {
       return userEntityMapper.selectByPrimaryKey(userId);
    }
}
