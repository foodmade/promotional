package com.seller66.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.seller66.admin.mapper")
public class Seller66AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Seller66AdminServerApplication.class, args);
    }
} 