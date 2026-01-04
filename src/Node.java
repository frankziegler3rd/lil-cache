/**
 * Node for LRU DLL
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package cache;

class Node<K> {
    
    private Node prev;
    private Node next;
    private K key;

    public Node(K key) {
        this.key = key;
        next = null;
        prev = null;
    }

    public Node getPrev() { return prev; }
    public void setPrev(Node prev) { this.prev = prev; }
    public Node getNext() { return next; }
    public void setNext(Node next) { this.next = next; }
    public K getKey() { return key; }
    public void setKey(K key) { this.key = key; }
}
