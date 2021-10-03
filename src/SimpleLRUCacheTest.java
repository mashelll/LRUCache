import org.junit.Test;

import static org.junit.Assert.assertNull;

public class SimpleLRUCacheTest {
    @Test
    public void simpleLRUCacheTest () {
        SimpleLRUCache<Integer, Integer> cache = new SimpleLRUCache<>(4);
        testCache(cache);
    }

    @Test
    public void threadSafeLRUCacheTest () {
        LRUCacheThreadSafe<Integer, Integer> cache = new LRUCacheThreadSafe<>(4);
        testCache(cache);
    }

    private void testCache(Cache<Integer, Integer> cache) {
        cache.put(0, 0);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        System.out.println(cache);
        cache.put(4, 4);
        System.out.println(cache);
        assertNull(cache.get(0));
    }
}
