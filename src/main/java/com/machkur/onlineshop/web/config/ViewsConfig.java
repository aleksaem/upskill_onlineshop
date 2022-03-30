package com.machkur.onlineshop.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class ViewsConfig {

    @Bean
    public org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver freeMarkerViewResolver() {
        org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver viewResolver = new org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver();
        viewResolver.setCache(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/views");
        return configurer;
    }
}
