package com.example.springsecurityapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// конфиг под изображения
@Configuration
public class Config implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("D:/Desktop/Плешка/Java/Spring project/SpringSecurityApplication/src/main/resources/static/img")
    private String imgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                // надо три слеша вместо двух
                .addResourceLocations("file:///" + uploadPath + "/", "file:///" + imgPath + "/");


    }

}