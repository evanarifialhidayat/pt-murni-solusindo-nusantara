package com.app.interview.murni.conf;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource(value = "classpath:application.properties",ignoreResourceNotFound=true)
//    , @PropertySource(value = "classpath:git.properties")
})
public class ServletInitializer extends SpringBootServletInitializer {

}
