/**
 * in-memory cache
 * has both TTL eviction (via ScheduledExecutorService) and capacity eviction algorithms
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package com.fz3rd.LilCache.cache;

import com.fz3rd.LilCache.cache.Entry;
import com.fz3rd.LilCache.cache.eviction.EvictionPolicy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Map;

public class InMemCache<K, V> implements Cache<K, V> {

    private final ConcurrentHashMap<K, Entry<V>> cache; // the data store
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(); // the dispatch for TTL
    private final EvictionPolicy<K> ep; // the capacity evictor
    private final int capacity; // the capacity
    private final long entryLifespan;

    public InMemCache(EvictionPolicy<K> ep, int capacity, long entryLifespan) {
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>(capacity);
        this.ep = ep;
        this.entryLifespan = entryLifespan;
        scheduler.scheduleAtFixedRate(this::evictExpiredEntries, 10, 10, TimeUnit.SECONDS);
    }

    /**
     * retrieve the value associated with the requested key
     */
    public V get(K key) {
        Entry<V> entry = cache.get(key);
        if(entry == null) return null;
        ep.onGet(key);
        return entry.getValue();
    }

    /**
     * retrieve the value associated with the requested key, or the default value passed by user
     */
    public V getOrDefault(K key, V value) {
        if(cache.containsKey(key)) {
            ep.onGet(key);
            return cache.get(key).getValue();
        }
        return value;
    }

    /**
     * store the KV pair in the cache. if the cache is at capacity and the key does not already exist, evict based on the policy.
     */
    public void put(K key, V value) {
        if(!cache.containsKey(key) && cache.size() >= capacity) {
            K evicted = ep.evict();
            cache.remove(evicted);
        }
        ep.onPut(key);
        cache.put(key, new Entry<V>(value, entryLifespan));
    }

    /**
     * delete a KV pair by key.
     */
    public void delete(K key) {
        if(cache.containsKey(key)) {
            ep.onDelete(key);
            cache.remove(key);
        }
    }

    /**
     * clear the cache
     */
    public void deleteAll() {
        for(K key : cache.keySet()) {
            ep.onDelete(key);
        }
        cache.clear();
    }

    /**
     * retrieve the size of the cache
     */
    public int size() { return cache.size(); }

    /**
     * TTL eviction 
     */
    public void evictExpiredEntries() {
        for(Map.Entry<K, Entry<V>> entry : cache.entrySet()) {
            K key = entry.getKey();
            Entry<V> value = entry.getValue();
            if(value.isExpired()) {
                cache.remove(key);
                ep.onDelete(key);
            }
        }
    }
}
