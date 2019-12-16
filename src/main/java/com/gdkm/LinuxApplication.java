package com.gdkm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.gdkm.dao")
@SpringBootApplication
public class LinuxApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinuxApplication.class, args);
    }

}
