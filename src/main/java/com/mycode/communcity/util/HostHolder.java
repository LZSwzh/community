package com.mycode.communcity.util;

import com.mycode.communcity.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户的信息，用于代替session对象。
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();
    //存数据
    public void setUser(User user){
        users.set(user);
    }
    //取数据
    public User getUser(){
        return users.get();
    }
    //清理数据
    public void clear(){
        users.remove();
    }
}
