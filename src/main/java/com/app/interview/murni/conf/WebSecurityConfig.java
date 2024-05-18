package com.app.interview.murni.conf;

import com.app.interview.murni.services.token.JwtRequestFilter;
import com.app.interview.murni.util.UtilParam;
import com.app.interview.murni.util.UtilReturn;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private static final Logger LOG = LogManager.getLogger(WebSecurityConfig.class);

    public WebSecurityConfig(UserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	
    	httpSecurity.headers().frameOptions();
    	httpSecurity.headers().xssProtection();
    	httpSecurity.headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(3156000);
    	httpSecurity.headers().contentTypeOptions();
    	
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,   "/api/registrasi").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/api/list").permitAll();
//    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/api/example").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,   "/api/login").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,   "/home").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,   "/user/registrasi").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/login").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/murni/{gagal}").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/list/view").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/user/example").permitAll();
    	
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/dashboard").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "**").permitAll();
    	
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,    "/ui/**").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,   "/user/save").permitAll();
    	httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,   "/user/update").permitAll();
    	
        httpSecurity.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET,"/user/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            Map<String, Object> responseMap = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(401);
            responseMap.put("error", true);
            responseMap.put("message", "Unauthorized");
            response.setHeader("content-type", "application/json");
            String responseMsg = mapper.writeValueAsString(responseMap);
            response.getWriter().write(responseMsg);
            LOG.error("Path unauthorized...",responseMsg);
        }).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}