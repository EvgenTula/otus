package ru.otus.hw6mycacheengine.cache;

public class Element<K, V> {

    private final byte[] array = new byte[1024 * 1024 * 1];

    private final K key;
    private final V val;
    public Element(K key, V val) {
        this.key = key;
        this.val = val;

    }

    public K getKey() {
        return this.key;
    }

    public V getVal() {
        return this.val;
    }
}
