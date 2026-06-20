package com.naviya.lru;

/**
 * Demo / manual test drive for LRUCache.
 * Run this class directly to see the cache behavior printed to console.
 */
public class LRUDemo {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println("After inserting 1,2,3:");
        System.out.println(cache); // [3=C] [2=B] [1=A]

        cache.get(1); // access 1 -> moves it to front (most recently used)
        System.out.println("\nAfter accessing key 1:");
        System.out.println(cache); // [1=A] [3=C] [2=B]

        cache.put(4, "D"); // capacity full -> evicts least recently used (key 2)
        System.out.println("\nAfter inserting 4 (capacity full, evicts LRU):");
        System.out.println(cache); // [4=D] [1=A] [3=C]

        System.out.println("\nGet evicted key 2: " + cache.get(2)); // null
    }
}
