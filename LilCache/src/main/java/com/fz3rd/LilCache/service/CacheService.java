/**
 * service class to perform cache business logic and keep controller thin
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package com.fz3rd.LilCache.service;

import org.springframework.stereotype.Service;
import com.fz3rd.LilCache.cache.Cache;
import com.fz3rd.LilCache.cache.InMemCache;
import com.fz3rd.LilCache.cache.eviction.EvictionPolicy;
import com.fz3rd.LilCache.cache.eviction.LRUPolicy;

@Service
public class CacheService {
    
    private final Cache<String, String> cache;

    public CacheService(Cache<String, String> cache) {
        this.cache = cache;
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public void delete(String key) {
        cache.delete(key);
    }

    public void deleteAll() {
        cache.deleteAll();
    }

    public int size() {
        return cache.size();
    }
}
