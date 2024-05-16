package com.app.interview.murni.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class UiConfig extends WebMvcConfigurerAdapter { 
   
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
        "/ui/**"
                )
                .addResourceLocations(
                        "classpath:/static/ui/"
                );
    } 
}