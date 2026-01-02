/**
 * Interface that defines the skeleton for different kinds of eviction policies like LRU, LFU, FIFO, etc.
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package cache;

public interface EvictionPolicy {
    void onGet(K key); // update eviction field
    void onPut(K key); // update eviction cache
    void evict(); // evict a key
}
