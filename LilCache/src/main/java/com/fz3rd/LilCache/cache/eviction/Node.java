/**
 * doubly-linked Node for LRU DLL
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package.com.fz3rd.LilCache.cache.eviction;

class Node<K> {

    /**
     * the previous node, the next node, and the key
     */
    private Node prev;
    private Node next;
    private K key;

    /**
     * node next/prev pointers are set after instantiation
     */
    public Node(K key) {
        this.key = key;
        next = null;
        prev = null;
    }

    /**
     * getters and setters
     */
    public Node getPrev() { return prev; }
    public void setPrev(Node prev) { this.prev = prev; }
    public Node getNext() { return next; }
    public void setNext(Node next) { this.next = next; }
    public K getKey() { return key; }
    public void setKey(K key) { this.key = key; }
}
