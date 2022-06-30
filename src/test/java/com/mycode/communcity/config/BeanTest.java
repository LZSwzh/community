package com.mycode.communcity.config;

import com.mycode.communcity.MybbsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = MybbsApplication.class)
public class BeanTest{
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void beanTest(){
        SimpleDateFormat sim = applicationContext.getBean(SimpleDateFormat.class);
        System.out.println(sim.format(new Date()));
    }
}
