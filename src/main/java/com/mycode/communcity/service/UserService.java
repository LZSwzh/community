package com.mycode.communcity.service;

import com.mycode.communcity.dao.UserMapper;
import com.mycode.communcity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User findUserByid(int id){
        return userMapper.selectById(id);
    }
}
