/**
 * JUnit test for InMemCache class
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package com.fz3rd.LilCache.cache;

import com.fz3rd.LilCache.cache.Cache;
import com.fz3rd.LilCache.cache.InMemCache;
import com.fz3rd.LilCache.cache.eviction.EvictionPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.Thread;
import java.lang.InterruptedException;

@ExtendWith(MockitoExtension.class)
class InMemCacheTest {

    @Mock EvictionPolicy<String> policy;
    Cache<String, String> cache;

    @BeforeEach void setup() {
        cache = new InMemCache<>(policy, 5, 500, 500); // 5 entries, 500 milliseconds per entry
    }

    @Test void basicPutAndGet() {
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        assertAll(
                () -> assertEquals(cache.get("a"), "1"),
                () -> assertEquals(cache.get("b"), "2"),
                () -> assertEquals(cache.get("c"), "3")
        );
    }

    @Test void getAndGetOrDefaultOnInvalidKey() {
        cache.put("key", "value");
        cache.put("kee", "valyoo");
        assertAll(
                () -> assertNull(cache.get("kei")),
                () -> assertEquals(cache.getOrDefault("kei", "valueh"), "valueh")
        );
    }

    @Test void capacityBasedEviction() {
        when(policy.evict()).thenReturn("0");
        for(int i = 0; i < 5; i++) {
            cache.put(String.valueOf(i), String.valueOf(i + 1));
        }
        cache.put("5", "6");
        assertEquals(cache.size(), 5);
    }

    @Test void delete() {
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        cache.delete("a");
        assertNull(cache.get("a"));
        cache.delete("b");
        assertNull(cache.get("b"));
        cache.delete("c");
        assertNull(cache.get("c"));
    }

    @Test void deleteAll() {
         for(int i = 0; i < 5; i++) {
            cache.put(String.valueOf(i), String.valueOf(i + 1));
        }
        cache.deleteAll();
        assertEquals(cache.size(), 0);
    }

    // TODO: TTL eviction test here
    @Test void testTTL() throws InterruptedException {
        cache.put("a", "doesntMatter");
        cache.put("b", "doesntMatter");
        
        Thread.sleep(1500);

        assertNull(cache.get("a"));
        assertNull(cache.get("b"));
    }
}
