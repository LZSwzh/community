package com.mycode.communcity.service;

import com.mycode.communcity.dao.DiscussPostMapper;
import com.mycode.communcity.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    /**
     * 将查询出来的userId和对应的用户名弄一块
     *      一种办法是协sql的时候关联
     *      第二种另写一个service根据id查询user，在dao调用。比较有利于后续使用redis来缓存。
     */
    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit){
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }
    public Integer findDiscussCount(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
