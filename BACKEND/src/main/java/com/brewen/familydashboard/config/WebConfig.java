package com.brewen.familydashboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Autorise tous les endpoints commençant par /api
                .allowedOrigins("http://localhost:4200") // Autorise le frontend Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Autorise les méthodes HTTP
                .allowedHeaders("*") // Autorise tous les headers
                .allowCredentials(true);
    }
}
