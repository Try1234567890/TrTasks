package me.tr.trtasks.registry;

import java.util.Map;

public abstract class TrRegistry<K, V> {

    public V retrieve(K key) {
        return getRegistry().get(key);
    }

    public V remove(K key) {
        return getRegistry().remove(key);
    }

    public V register(K key, V value) {
        if (getRegistry().containsKey(key)) {
            throw new IllegalStateException("Trying to register a key that already exists");
        }
        getRegistry().put(key, value);
        return value;
    }

    protected abstract Map<K, V> getRegistry();
}
