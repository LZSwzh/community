package com.mycode.communcity.controller.interceptor;

import com.mycode.communcity.entity.LoginTicket;
import com.mycode.communcity.entity.User;
import com.mycode.communcity.service.UserService;
import com.mycode.communcity.util.CookieUtil;
import com.mycode.communcity.util.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    /**
     * 一开始获取user
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = CookieUtil.getValue(request,"ticket");
        if (ticket!=null){
            LoginTicket loginTicket = userService.findLoginTicket(ticket);//查询凭证
            //判断是否有效
            if (loginTicket!=null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())){
                //根据凭证查询用户
                User user = userService.findUserByid(loginTicket.getUserId());
                //暂存user(在本次请求中持有user)
                hostHolder.setUser(user);
            }
        }

        return true;
    }
    /**
     * 在模板引擎调用前，将user放到model里。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null){
            modelAndView.addObject("loginUser",user);
        }

    }
    /**
     * 在整个请求结束的时候清楚数据
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
