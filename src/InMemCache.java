/**
 * In-memory cache
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package cache;

import cache.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class InMemCache<K, V> implements Cache<K, V> {

    private final ConcurrentHashMap<K, Entry<V>> cache;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final EvictionPolicy ep;
    private final int capacity;

    public InMemCache(EvictionPolicy ep, int capacity) {
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>(capacity);
        this.ep = ep;
        scheduler.scheduleAtFixedRate(this::evictExpiredEntries, 10, 10, TimeUnit.SECONDS);
    }

    public V get(K key) {
        Entry<V> value = cache.get(key);
        if(value == null) return null;
        ep.onGet(key);
        return entry.getValue();
    }

    public V getOrDefault(K key, V value) {
        if(cache.containsKey(key)) {
            ep.onGet(key);
            return cache.get(key).getValue();
        }
        return value;
    }

    public void put(K key, V value) {
        if(!cache.containsKey(key) && cache.size() >= capacity) {
            K evicted = ep.evict();
            cache.remove(evicted);
        }
        ep.onPut(key);
        cache.put(key, new Entry<V>(value));
    }

    public void delete(K key) {
        if(cache.containsKey(key)) {
            ep.onDelete(key);
            cache.remove(key);
        }
    }

    public void deleteAll() {
        for(K key : cache.keySet()) {
            ep.onDelete(key);
        }
        cache.clear();
    }

    public int size() { return cache.size(); }

    public void evictExpiredEntries() {
        for(Map.Entry<K, V> entry : cache.entrySet()) {
            K key = entry.getKey();
            Entry<V> value = entry.getValue();
            if(value.isExpired()) {
                cache.remove(key);
                ep.onDelete(key);
            }
        }
    }
}
