package com.radical3d.turismapp.TurismApp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class AppHeadersBean {
    @Value("${application.header-pag-count-name}")
    private String HEADER_PAGINATION_COUNT_NAME;

    @Value("${application.header-pag-page-name}")
    private String HEADER_PAGINATION_PAGE_NAME;

    @Value("${application.header-pag-limit-name}")
    private String HEADER_PAGINATION_LIMIT_NAME;


}
