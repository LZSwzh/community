package com.mycode.communcity.controller;

import com.mycode.communcity.entity.DiscussPost;
import com.mycode.communcity.entity.Page;
import com.mycode.communcity.entity.User;
import com.mycode.communcity.service.DiscussPostService;
import com.mycode.communcity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        /**
         * 为什么page不用写道model中呢？方法调用前，mvc自动实例化mvc和page；且将page注入给page，所以无需model.addA...
         */
        page.setRows(discussPostService.findDiscussCount(0));
        System.out.println(page.getCurrent());
        page.setPath("/index");
        List<DiscussPost> lists = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        //上句查到的数据不完整
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        //将数据封装到List<Map<~,~>>
        if (lists!=null){
            for (DiscussPost post:lists) {
                Map<String,Object> map = new HashMap<>();
                map.put("post",post);
                User user = userService.findUserByid(post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }
}
