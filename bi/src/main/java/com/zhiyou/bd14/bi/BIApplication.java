package com.zhiyou.bd14.bi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.zhiyou.bd14.bi.dao")
public class BIApplication extends SpringBootServletInitializer{

    public static void main(String[] args){

        SpringApplication.run(BIApplication.class);
    }
}
