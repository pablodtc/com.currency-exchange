package com.currencyexchange.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

  @Bean
  CacheManager concurrentMapCacheManager() {
		return new ConcurrentMapCacheManager("currency");
	}
}
