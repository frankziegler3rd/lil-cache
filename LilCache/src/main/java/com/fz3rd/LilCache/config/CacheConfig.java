/**
 * configs for the Cache
 * dependency injections
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package com.fz3rd.LilCache.config;

import com.fz3rd.LilCache.cache.eviction.*;
import com.fz3rd.LilCache.cache.Cache;
import com.fz3rd.LilCache.cache.InMemCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class CacheConfig {

    @Bean
    public EvictionPolicy<String> evictionPolicy() {
        return new LRUPolicy<String>();
    }

    /**
     * Cache configured with capacity 100 and expiration of 1 hour evicted at a rate of 1 hour
     */
    @Bean
    public Cache<String, String> cache(EvictionPolicy<String> ep) {
        return new InMemCache<>(ep, 100, 3600000, 3600000);
    }
}
