package com.boss.spider.position;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.boss.spider.position.dao")
public class PositionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PositionApplication.class, args);
    }

}
