package com.naviya.lru;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    @Test
    void testBasicPutAndGet() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        assertEquals("A", cache.get(1));
        assertEquals("B", cache.get(2));
    }

    @Test
    void testEvictionOfLeastRecentlyUsed() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C"); // evicts 1, since 2 is more recently touched than 1
        assertNull(cache.get(1));
        assertEquals("B", cache.get(2));
        assertEquals("C", cache.get(3));
    }

    @Test
    void testAccessMarksAsRecentlyUsed() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.get(1);       // touch 1 -> now 2 is least recently used
        cache.put(3, "C");  // evicts 2, not 1
        assertNull(cache.get(2));
        assertEquals("A", cache.get(1));
        assertEquals("C", cache.get(3));
    }

    @Test
    void testUpdatingExistingKeyRefreshesPosition() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(1, "A-updated"); // touches 1, 2 becomes LRU
        cache.put(3, "C");          // evicts 2
        assertNull(cache.get(2));
        assertEquals("A-updated", cache.get(1));
    }

    @Test
    void testGetOnMissingKeyReturnsNull() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        assertNull(cache.get(99));
    }

    @Test
    void testCapacityOne() {
        LRUCache<Integer, String> cache = new LRUCache<>(1);
        cache.put(1, "A");
        cache.put(2, "B"); // evicts 1 immediately
        assertNull(cache.get(1));
        assertEquals("B", cache.get(2));
    }

    @Test
    void testSizeTracksEntries() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        assertEquals(0, cache.size());
        cache.put(1, "A");
        cache.put(2, "B");
        assertEquals(2, cache.size());
        cache.put(3, "C");
        cache.put(4, "D"); // triggers eviction, size stays at capacity
        assertEquals(3, cache.size());
    }

    @Test
    void testInvalidCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new LRUCache<Integer, String>(0));
        assertThrows(IllegalArgumentException.class, () -> new LRUCache<Integer, String>(-5));
    }
}
