package com.mycode.communcity;

import com.mycode.communcity.config.AlphaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class MybbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybbsApplication.class, args);
    }

}
