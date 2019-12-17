package com.gdkm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    public projectUrl projectUrl;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(projectUrl.getImg()+"**").addResourceLocations("file:"+projectUrl.getImgUrl());
        registry.addResourceHandler(projectUrl.getShipin()+"**").addResourceLocations("file:"+projectUrl.getShipinUrl());
        registry.addResourceHandler(projectUrl.getKejian()+"**").addResourceLocations("file:"+projectUrl.getKejianUrl());
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
