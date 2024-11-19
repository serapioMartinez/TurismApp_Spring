package com.radical3d.turismapp.TurismApp.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsWebConfiguration {

    
    @Value("${application.allowed-origins}")
    private String allowed_origins[];

    @Value("${application.header-pag-count-name}")
    private String COUNT_HEADER;

    @Value("${application.header-pag-page-name}")
    private String PAGE_HEADER;

    @Value("${application.header-pag-limit-name}")
    private String LIMIT_HEADER;

    @Bean(name = "customCors")
    CorsConfigurationSource customCorsConfigurationSource(){
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedMethods(List.of(CorsConfiguration.ALL));
        corsConfig.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        corsConfig.setAllowedOrigins(List.of(allowed_origins));
        corsConfig.setExposedHeaders(List.of(COUNT_HEADER, PAGE_HEADER, LIMIT_HEADER));
        corsConfig.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

}
