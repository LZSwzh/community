package com.mycode.communcity.controller.interceptor;

import com.mycode.communcity.Annotation.LoginRequired;
import com.mycode.communcity.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;//转型
            Method method = handlerMethod.getMethod();//获取拦截到的method对象
            LoginRequired annotation = method.getAnnotation(LoginRequired.class);//获取注解
            if(annotation!=null && hostHolder.getUser() == null){
                //重定向到登录页面
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }
        }
        return true;
    }
}
