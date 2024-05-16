package com.app.interview.murni.conf;

import java.util.Locale;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@ComponentScan(basePackages = 
{
		"com.app.interview.murni.controller"
//		,"com.baeldung.git"
		})
public class WebConfig  extends WebMvcConfigurerAdapter  {
@Bean
  public LocaleResolver localeResolver(){
       SessionLocaleResolver localeResolver = new SessionLocaleResolver();
       localeResolver.setDefaultLocale(Locale.US);
       return  localeResolver;
   }

   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
       LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
       localeChangeInterceptor.setParamName("lang");
       return localeChangeInterceptor;
   }

   public void addInterceptors(InterceptorRegistry registry){
       registry.addInterceptor(localeChangeInterceptor());
   }
}