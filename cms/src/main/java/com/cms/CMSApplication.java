package com.cms;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//扫描接口并自动生成
@MapperScan(basePackages = {"com.cms.sys.mapper"})
public class CMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(CMSApplication.class);
    }
}
