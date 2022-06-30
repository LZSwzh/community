package com.mycode.communcity.mapper;

import com.mycode.communcity.dao.DiscussPostMapper;
import com.mycode.communcity.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class tests2 {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    void test1(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(0, 0, 10);
        discussPosts.forEach(System.out::println);
    }
    @Test
    void test2(){
        int i = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(i);
    }
}
