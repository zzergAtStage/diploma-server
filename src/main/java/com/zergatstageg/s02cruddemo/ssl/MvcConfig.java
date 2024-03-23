package com.zergatstageg.s02cruddemo.ssl;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Simple try of using MVC configurator
 * @author ZergAtStage
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("ssl/public/welcome");
        registry.addViewController("/").setViewName("ssl/public/welcome");
        registry.addViewController("/login").setViewName("ssl/public/login");
    }

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @param registry
     * @see ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**",
                        "/img/**")
                .addResourceLocations("classpath:/static/css/",
                        "classpath:/static/img/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
