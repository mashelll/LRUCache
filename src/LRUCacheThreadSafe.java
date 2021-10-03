import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCacheThreadSafe<K, V> implements Cache<K, V>{
    private final int limit;
    private final Map<K, V> cache;
    private final Queue<K> order;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public LRUCacheThreadSafe(int limit){
        this.limit = limit;
        cache = new ConcurrentHashMap<>();
        order = new ConcurrentLinkedQueue<>();
    }

    public V get(K key) {
        readWriteLock.readLock().lock();
        try {
            V value = cache.get(key);
            if (value != null) {
                if (order.remove(key)) {
                    order.add(key);
                }
            }
            return value;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public V put(K key, V value) {
        readWriteLock.writeLock().lock();
        try {
            if (cache.containsKey(key)) {
                order.remove(key);
            }
            if (order.size() == limit) {
                K expiredKey = order.poll();
                cache.remove(expiredKey);
            }
            cache.put(key, value);
            order.add(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return value;
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
