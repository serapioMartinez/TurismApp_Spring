package com.radical3d.turismapp.TurismApp.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.radical3d.turismapp.TurismApp.configuration.AppHeadersBean;

@Configuration
public class CorsWebConfiguration {

    
    @Value("${application.allowed-origins}")
    private String allowed_origins[];

    @Autowired
    private AppHeadersBean appHeaders;

    @Bean(name = "customCors")
    CorsConfigurationSource customCorsConfigurationSource(){
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedMethods(List.of(CorsConfiguration.ALL));
        corsConfig.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        corsConfig.setAllowedOrigins(List.of(allowed_origins));
        corsConfig.setExposedHeaders(List.of(appHeaders.getHEADER_PAGINATION_COUNT_NAME(), 
                                            appHeaders.getHEADER_PAGINATION_PAGE_NAME(),
                                            appHeaders.getHEADER_PAGINATION_LIMIT_NAME()));
        corsConfig.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

}
