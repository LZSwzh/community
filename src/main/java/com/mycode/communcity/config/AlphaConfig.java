package com.mycode.communcity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;

@Configuration
public class AlphaConfig {
    @Bean
    public SimpleDateFormat implSimpleDateFormate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
