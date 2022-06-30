package com.mycode.communcity.mapper;

import com.mycode.communcity.dao.UserMapper;
import com.mycode.communcity.entity.User;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class tests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
    @Test
    public void testSelectUser2(){
        User user = userMapper.selectByName("liubei");
        System.out.println(user);
    }
    @Test
    public void testSelectUser3(){
        User user = userMapper.selectByEmail("nowcoder12@sina.com");
        System.out.println(user);
    }
    @Test
    public void testInserUser(){
        User user = new User("sdfa", "asdfasdfa", null, null, 0, 0, null, null, null);
        int i = userMapper.insertUser(user);
        System.out.println(i);
    }
    @Test
    public void testUpdate(){
        int i = userMapper.updateStatus(1, 0);
        System.out.println(i);
    }
}
