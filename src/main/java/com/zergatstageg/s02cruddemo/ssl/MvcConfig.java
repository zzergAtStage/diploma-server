package com.zergatstageg.s02cruddemo.ssl;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Simple try of using MVC configurator
 * @author ZergAtStage
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("ssl/welcome");
        registry.addViewController("/").setViewName("ssl/welcome");
        registry.addViewController("/login").setViewName("ssl/login");
    }
}