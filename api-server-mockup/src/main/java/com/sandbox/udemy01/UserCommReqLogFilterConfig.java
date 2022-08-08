package com.sandbox.udemy01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class UserCommReqLogFilterConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        
    	CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        
    	filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(100*1000);
        filter.setIncludeHeaders(true);
        // filter.setBeforeMessagePrefix("ALBERTO!!: REQUEST: ");
        // filter.setAfterMessagePrefix("ALBERTO!!: REQUEST: ");
        
        return filter;
    }
}

