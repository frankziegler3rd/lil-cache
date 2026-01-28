/**
 * JUnit test for LRUPolicy
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

import com.fz3rd.LilCache.cache.eviction.LRUPolicy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LRUPolicyTest {
    
    @Test
    void evictsLRU() {
        LRUPolicy<String> lru = new LRUPolicy<>();
        lru.onPut("a");
        lru.onPut("b");
        lru.onPut("c");
        lru.onPut("d");
        lru.onGet("b");
        lru.onGet("c");
        lru.onGet("d");
        String evicted = lru.evict(); // "a" is LRU
        assertEquals(evicted, "a");
    }

    @Test
    void evictsOnEmpty() {
        LRUPolicy<String> lru = new LRUPolicy<>();
        String key = lru.evict();
        assertNull(key);
    }

    @Test
    void rePut() {
        LRUPolicy<String> lru = new LRUPolicy<>();
        lru.onPut("a");
        lru.onPut("b");
        lru.onPut("c");
        lru.onPut("a");
        String evicted = lru.evict(); // "b" is LRU
        assertEquals(evicted, "b");
    }

    @Test
    void onDelete() {
        LRUPolicy<String> lru = new LRUPolicy<>();
        lru.onPut("a");
        lru.onPut("b");
        lru.onPut("c");
        lru.onDelete("a");
        lru.onDelete("b");
        lru.onDelete("c");
        String evicted = lru.evict();
        assertNull(evicted);
    }

}
