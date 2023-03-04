package kingland.task003;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImprovedMap<K, V> implements Map<K, V> {
    private final HashMap<K, V> map;

    public ImprovedMap(Map<K, V> map) {
        this.map = new HashMap<>();
    }

    public V put(K key, V value) {
        synchronized (map) {
            map.put(key, value);
        }
        return value;
    }

    @Override
    public V remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    public int size() {
        synchronized (map) {
            return map.size();
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object o) {
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    @Override
    public V get(Object o) {
        return null;
    }
}
