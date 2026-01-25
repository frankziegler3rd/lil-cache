/**
 * Cache interface
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package.cache;

/**
 * cache methods mock a basic K-V store.
 */
public interface Cache<K, V> {
    V get(K key);
    V getOrDefault(K key, V default);
    void put(K key, V value);
    void delete(K key);
    void deleteAll();
    int size();
}
