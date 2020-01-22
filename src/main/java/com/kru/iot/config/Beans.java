package com.kru.iot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author kru on 23-1-20
 * @project events-query-service
 */

@Configuration
public class Beans {
    @Bean
    SimpleDateFormat sdf() {
        return  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    }
}
