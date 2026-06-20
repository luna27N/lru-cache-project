# LRU Cache — Java Implementation

A custom **LRU (Least Recently Used) Cache** built from scratch using a `HashMap` + **Doubly Linked List**, achieving **O(1)** time complexity for both `get()` and `put()` operations.

This is a classic data-structure-design interview question, frequently asked by product-based companies and used to test understanding of Collections internals.

## Why HashMap Alone Isn't Enough

A `HashMap` gives O(1) lookup but has no concept of "recency of use." To evict the *least recently used* item in O(1), we need a structure that tracks usage order too — a **doubly linked list** does this: move accessed nodes to the front, evict from the tail.

## Design

| Component | Purpose |
|---|---|
| `HashMap<K, Node>` | O(1) lookup of any node by key |
| Doubly Linked List | O(1) move-to-front and O(1) remove-from-tail |
| `head` (dummy) | Most recently used side |
| `tail` (dummy) | Least recently used side (evicted first) |

Dummy head/tail sentinel nodes eliminate null-checking edge cases (empty list, single node, etc.).

## Complexity

| Operation | Time | Space |
|---|---|---|
| `get(key)` | O(1) | — |
| `put(key, value)` | O(1) | — |
| Overall | — | O(capacity) |

## Project Structure

```
lru-cache-project/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/naviya/lru/
    │   ├── LRUCache.java     # Core implementation
    │   └── LRUDemo.java      # Runnable demo
    └── test/java/com/naviya/lru/
        └── LRUCacheTest.java # JUnit 5 test suite
```

## Usage

```java
LRUCache<Integer, String> cache = new LRUCache<>(3);

cache.put(1, "A");
cache.put(2, "B");
cache.put(3, "C");

cache.get(1);        // touches key 1, marks it as most recently used

cache.put(4, "D");    // capacity full -> evicts least recently used (key 2)

cache.get(2);         // returns null, was evicted
```

## Running the Project

**Run the demo:**
```bash
mvn compile exec:java -Dexec.mainClass="com.naviya.lru.LRUDemo"
```

**Run the tests:**
```bash
mvn test
```

## Alternative: Java's Built-in Shortcut

Java's `LinkedHashMap` supports LRU eviction natively via `removeEldestEntry()` and access-order mode:

```java
LinkedHashMap<Integer, String> lru = new LinkedHashMap<>(capacity, 0.75f, true) {
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > capacity;
    }
};
```

This implementation builds the mechanism manually to demonstrate the underlying data structure design — a common interview follow-up is being asked to explain *how* `LinkedHashMap` achieves this internally.

## Notes

- This implementation is **not thread-safe**. For concurrent use, wrap critical sections with `synchronized`, or use `ConcurrentHashMap` with a separate locking strategy for the linked list.
- Built as part of Java Core & Collections Framework interview preparation.

## Author

Naviya Kanniyapan ([@luna27](https://github.com/luna27))
