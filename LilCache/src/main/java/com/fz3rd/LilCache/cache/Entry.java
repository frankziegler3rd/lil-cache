/**
 * the cache "value", associated with a key in the cache.
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package com.fz3rd.LilCache.cache;

public class Entry<V> {
    
    private V value;
    private long expiration;

    public Entry(V value) {
        this.value = value;
        // current time + 12 hours
        // maybe should not have a hard coded lifespan but ill come back to it
        expiration = System.currentTimeMillis()+43200000; 
    }

    // getters and setters
    public V getValue() { return value; }
    public void setValue(V value) { this.value = value; }
    public long getExpiration() { return expiration; }
    public void setExpiration() { this.expiration = expiration; }

    /**
     * returns whether or not entry is expired
     */
    public boolean isExpired() { return System.currentTimeMillis() > expiration; }
}
