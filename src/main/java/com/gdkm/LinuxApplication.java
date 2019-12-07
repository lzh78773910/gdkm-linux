package com.gdkm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.gdkm.dao")
@SpringBootApplication
@EnableScheduling
public class LinuxApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinuxApplication.class, args);
    }

}
