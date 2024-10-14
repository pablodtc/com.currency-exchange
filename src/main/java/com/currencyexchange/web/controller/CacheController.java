package com.currencyexchange.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CacheController {

  private final CacheManager cacheManager;

  @GetMapping("/cache")
  public Object getAllEntriesFromCache() {
    return cacheManager.getCacheNames()
        .stream()
        .map(cacheName -> cacheManager.getCache(cacheName))
        .map(Cache::getNativeCache);
  }
}
