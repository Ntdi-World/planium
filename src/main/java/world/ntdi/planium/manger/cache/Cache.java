package world.ntdi.planium.manger.cache;

import world.ntdi.planium.manger.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
    private Map<K, CacheItem> map;
    private CacheItem first, last;
    private int size;
    private final int CAPACITY;
    private int hitCount = 0;
    private int missCount = 0;

    public Cache(int capacity) {
        CAPACITY = capacity;
        map = new HashMap<>(CAPACITY);
    }

    public void put(K key, V value) {
        CacheItem<K, V> node = new CacheItem<>(key, value);

        if (!map.containsKey(key)) {
            if (size() >= CAPACITY) {
                deleteNode(first);
            }
            addNodeToLast(node);
        }
        map.put(key, node);
    }

    @SuppressWarnings("all") // Generics aren't very favorable, but my eyes are.
    public V get(K key) {
        if (!map.containsKey(key)) return null;

        CacheItem node = (CacheItem) map.get(key);
        node.incrementHitCount();
        reorder(node);
        return (V) node.getValue();
    }


    public int size() {
        return size;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    public Map<K, CacheItem> getMap() {
        return map;
    }

    private void deleteNode(CacheItem<K, V> node) {
        if (node == null) return;
        if (last == node) last = node.getPrev();
        if (first == node) first = node.getNext();

        ((Image) node.getValue()).getFile().delete();

        map.remove(node.getKey());
        node = null; // Collected by GC
        size--;
    }

    private void addNodeToLast(CacheItem<K, V> node) {
        if (last != null) {
            last.setNext(node);
            node.setPrev(last);
        }

        last = node;
        if (first == null) first = node;
        size++;
    }

    private void addNodeToFirst(CacheItem node) {
        if (first != null) {
            node.setNext(first);
            first.setPrev(node);
        }
        first = node;

        if (last == null) last = node;

        size++;
    }

    @SuppressWarnings("all")
    private void reorder(CacheItem<K, V> node) {
        if (last == node) return;

        CacheItem nextNode = node.getNext();

        while (nextNode != null) {
            if (nextNode.getHitCount() > node.getHitCount()) break;

            if (first == node) first = nextNode;
            if (node.getPrev() != null) node.getPrev().setNext(nextNode);

            nextNode.setPrev(node.getPrev());
            node.setPrev(nextNode);
            node.setNext(nextNode.getNext());

            if (nextNode.getNext() != null) nextNode.getNext().setPrev(node);

            nextNode.setNext(node);
            nextNode = node.getNext();
        }

        if (node.getNext() == null) last = node;
    }
}
