package com.hkx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@org.mybatis.spring.annotation.MapperScan("com.hkx.dao") //用于自定义Mapper的扫描
@tk.mybatis.spring.annotation.MapperScan("com.hkx.dao")
@org.mybatis.spring.annotation.MapperScan("com.hkx.dao") //用于自定义Mapper的扫描,必须卸载后面
public class YingxHkxApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxHkxApplication.class, args);
    }

}
