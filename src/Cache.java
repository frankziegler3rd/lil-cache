/**
 * Cache interface
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package.cache;

public interface Cache<K, V> {
    V get(K key);
    V getOrDefault(K key, V default);
    void put(K key, V value);
    void delete(K key);
    void deleteAll();
    int size();
}
