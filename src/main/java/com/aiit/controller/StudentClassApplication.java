package com.aiit.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.DigestUtils;

@MapperScan("com.aiit.mapper")
@SpringBootApplication
//@EnableScheduling   // 开启定时功能
@ComponentScan("com.aiit")
public class StudentClassApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentClassApplication.class, args);
    }
}
