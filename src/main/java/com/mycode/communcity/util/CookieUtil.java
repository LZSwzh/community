package com.mycode.communcity.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    public static String getValue(HttpServletRequest request,String name){
        if (request == null || name == null){
            throw new IllegalArgumentException("参数为空!");
        }
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            logger.debug("cookies中的数据为null");
            return null;
        }
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
