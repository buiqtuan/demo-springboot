package com.tawk.fun.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Configuration
@EnableCaching
public class BeanContainer {

    @Value("${cache.name.chatinfolist}")
    private String CACHE_NAME_CHAT_INFO_LIST;

    @Bean
    public RestTemplate restTemplate() {
        return (new RestTemplateBuilder())
                .setConnectTimeout(Duration.ofMillis(10000))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(this.CACHE_NAME_CHAT_INFO_LIST);
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        DateTimeFormatter dtf = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .toFormatter();

        return dtf;
    }

    @Bean
    public DateTimeFormatter dateTimeFormatterYYYYMMDD() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
}
