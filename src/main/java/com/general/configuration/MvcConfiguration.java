package com.general.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

@Override
       public void addCorsMappings(CorsRegistry registry) {

             registry.addMapping("/url/**")
             .allowedOrigins("*")
             .allowedMethods("PUT", "DELETE","POST","OPTIONS","GET");
             registry.addMapping("/resources/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET");

       }


}
