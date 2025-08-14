package com.thaipulse.newsapp.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig(
            @Value("${weather.cache.ttl-minutes:10}") long ttl
    ) {
        return Caffeine.newBuilder()
                .expireAfterWrite(ttl, TimeUnit.MINUTES)
                .maximumSize(1000);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager mgr = new CaffeineCacheManager("weatherByCity", "weatherByCoords");
        mgr.setCaffeine(caffeine);
        return mgr;
    }
}
