package com.naviya.lru;

import java.util.HashMap;

/**
 * LRU (Least Recently Used) Cache implementation.
 *
 * Design:
 *  - HashMap<K, Node>  -> O(1) lookup of any node by key
 *  - Doubly Linked List -> O(1) move-to-front and O(1) remove-from-tail
 *
 * head side  = most recently used
 * tail side  = least recently used (evicted first)
 *
 * Time complexity: O(1) for both get() and put()
 * Space complexity: O(capacity)
 *
 * @param <K> key type
 * @param <V> value type
 */
public class LRUCache<K, V> {

    // Doubly linked list node
    private class Node {
        K key;
        V value;
        Node prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final HashMap<K, Node> map;
    private final Node head; // dummy head sentinel
    private final Node tail; // dummy tail sentinel

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Returns the value for the given key, or null if not present.
     * Accessing a key marks it as most recently used.
     */
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node node = map.get(key);
        moveToFront(node);
        return node.value;
    }

    /**
     * Inserts or updates a key-value pair.
     * If the cache is at capacity, evicts the least recently used entry.
     */
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            moveToFront(node);
            return;
        }

        if (map.size() == capacity) {
            Node lru = tail.prev;
            removeNode(lru);
            map.remove(lru.key);
        }

        Node newNode = new Node(key, value);
        map.put(key, newNode);
        addToFront(newNode);
    }

    /** Current number of entries in the cache. */
    public int size() {
        return map.size();
    }

    // ---------- internal helpers ----------

    private void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addToFront(node);
    }

    /** Returns cache contents ordered from most-recently to least-recently used. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node curr = head.next;
        while (curr != tail) {
            sb.append("[").append(curr.key).append("=").append(curr.value).append("] ");
            curr = curr.next;
        }
        return sb.toString().trim();
    }
}
