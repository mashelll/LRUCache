import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleLRUCache<K, V> extends LinkedHashMap<K, V> implements Cache<K, V>{
    private final int limit;

    public SimpleLRUCache(final int limit) {
        super(limit + 1, 1.0f, true);
        this.limit = limit;
    }
    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
        return super.size() > limit;
    }

    @Override
    public V put(K key, V value) {
        super.put(key, value);
        return value;
    }
}