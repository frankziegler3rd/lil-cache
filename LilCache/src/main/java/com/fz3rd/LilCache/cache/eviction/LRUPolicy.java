/**
 * LRU eviction policy
 * contains key store that follows LRU
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package com.fz3rd.LilCache.cache.eviction;

import com.fz3rd.LilCache.cache.eviction.Node;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class LRUPolicy<K> implements EvictionPolicy<K> {
    
    /**
     * cache the current list of keys (currently without capacity constraints). it maintains pointers to the important nodes in the linked list.
     * the right node is directly to the right of the MRU.
     * the left node is directly to the left of the LRU.
     * everything in between are other keys in the custom LL, in recency order.  
     */
    private Map<K, Node<K>> cache;
    private Node<K> right;
    private Node<K> left;

    public LRUPolicy() {
        cache = new HashMap<>();
        right = new Node<K>(null);
        left = new Node<K>(null);
        right.prev = left;
        left.next = right;
    }
    
    /**
     * helper method
     * wire previous node to next node and next node to previous node.
     */
    private K remove(Node<K> node) {
        Node<K> prev = node.prev;
        Node<K> next = node.next;
        prev.next = next;
        next.prev = prev;
        return node.getKey();
    }

    /**
     * helper method
     * insert given node to the left of the right node, which is the position of the MRU. 
     */
    private void insert(Node<K>node) {
        Node<K> prev = right.prev;
        prev.next = node;
        node.prev = prev;
        node.next = right;
        right.prev = node;
    }

    /**
     * moves the node with the requested key to the front, making it the MRU.
     * does so by removing it from its place in the list and inserting it at the front.
     */
    public synchronized void onGet(K key) {
        if(cache.containsKey(key)) {
            Node<K> node = cache.get(key);
            remove(node);
            insert(node);
        }
    }

    /**
     * creates a new LL node at the front (the MRU). removes the node containing the same key if it exists
     */
    public synchronized void onPut(K key) {
        if(cache.containsKey(key)) {
            remove(cache.get(key));
        }
        Node<K> node = new Node<>(key);
        insert(node);
        cache.put(key, node);
    }

    /**
     * remove a key from the LRU cache and the custom LL
     */
    public synchronized void onDelete(K key) {
        if(cache.containsKey(key)) {
            Node<K> nodeToRemove = cache.get(key);
            cache.remove(key);
            remove(nodeToRemove);
        }
    }

    /**
     * removes the node to the right of the leftmost node (so long as the list is nonempty)
     */
    public synchronized K evict() {
        K key = null;
        if(left.next != right) {
            key = remove(left.next);
        }
        return key;
    }

}
