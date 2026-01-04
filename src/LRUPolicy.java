/**
 * LRU eviction policy
 * contains key store that follows LRU
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package cache;

import cache.Node;
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
    private Node right;
    private Node left;

    public LRUPolicy() {
        cache = new HashMap<>();
        right = new Node(null);
        left = new Node(null);
        right.prev = left;
        left.next = right;
    }
    
    /**
     * helper method
     * wire previous node to next node and next node to previous node.
     */
    private void remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    /**
     * helper method
     * insert given node to the left of the right node, which is the position of the MRU. 
     */
    private void insert(Node node) {
        Node prev = right.prev;
        prev.next = node;
        node.prev = prev;
        node.next = right;
        right.prev = node;
    }

    /**
     * moves the node with the requested key to the front, making it the MRU.
     * does so by removing it from its place in the list and inserting it at the front.
     */
    public void synchronized onGet(K key) {
        if(cache.containsKey(key)) {
            Node node = cache.get(key);
            remove(node);
            insert(node);
        }
    }

    /**
     * creates a new LL node at the front (the MRU). removes the node containing the same key if it exists
     */
    public void synchronized onPut(K key) {
        if(cache.containsKey(key)) {
            remove(cache.get(key));
        }
        Node node = new Node(key);
        insert(node);
    }

    /**
     * removes the node to the right of the leftmost node (so long as the list is nonempty)
     */
    public K synchronized evict() {
        if(left.next != right) {
            remove(left.next);
        }
    }

}
