package libcore.schedule;

import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.infrastructure.AbstractStorage;
import com.dvsnier.cache.infrastructure.CacheStorage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * ScheduledLruCacheTest
 * Created by dovsnier on 2019-07-26.
 */
public class ScheduledLruCacheTest {

    ScheduledLruCache<String, Object> cache;
    String KEY_1 = "key_1";
    String KEY_2 = "key_2";
    String KEY_3 = "key_3";


    @Before
    public void setUp() throws Exception {
        cache = new ScheduledLruCache<>(Double.valueOf(CacheStorage.INSTANCE().getFormatted(32, AbstractStorage.SCU.M)).intValue());
    }

    @Test
    public void get() {
        long value = System.currentTimeMillis();
        Object o1 = cache.put(KEY_1, value);
        Assert.assertNull(o1);
        Object o2 = cache.get(KEY_1);
        Assert.assertNotNull(o2);
    }

    @Test
    public void put() {
        long value = System.currentTimeMillis();
        Object o1 = cache.put(KEY_1, value);
        Assert.assertNull(o1);
        Object o2 = cache.put(KEY_1, System.currentTimeMillis());
        Assert.assertNotNull(o2);
        Assert.assertEquals(o2, value);
    }

    @Test
    public void put_of_scheduled() {
        long value = System.currentTimeMillis();
        Object o1 = cache.put(KEY_1, value);
        Assert.assertNull(o1);
        Object o2 = cache.put(KEY_2, System.currentTimeMillis(), 3, TimeUnit.SECONDS);
        Assert.assertNull(o2);
        Object o3 = cache.get(KEY_2);
        Assert.assertNotNull(o3);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o4 = cache.get(KEY_2);
        Assert.assertNull(o4);
        Assert.assertNotEquals(o3, o4);
        Object o5 = cache.put(KEY_3, System.currentTimeMillis());
        Assert.assertNull(o5);
        Object o6 = cache.get(KEY_3);
        Assert.assertNotNull(o6);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o7 = cache.get(KEY_3);
        Assert.assertNotNull(o7);
        Assert.assertEquals(o6, o7);
    }

    @Test
    public void remove() {
        long value = System.currentTimeMillis();
        Object o1 = cache.put(KEY_1, value);
        Assert.assertNull(o1);
        Object o2 = cache.remove(KEY_1);
        Assert.assertNotNull(o2);
    }

    @Test
    public void size() {
        long value = System.currentTimeMillis();
        Object o1 = cache.put(KEY_1, value);
        Assert.assertNull(o1);
        int size = cache.size();
        boolean sizeValue = size > 0;
        Assert.assertTrue(sizeValue);
    }

    @Test
    public void snapshot() {
        Map<String, Object> snapshot0 = cache.snapshot();
        Assert.assertTrue((snapshot0.size() == 0));
        long value = System.currentTimeMillis();
        Object o1 = cache.put(KEY_1, value);
        Assert.assertNull(o1);
        Map<String, Object> snapshot1 = cache.snapshot();
        Assert.assertTrue((snapshot1.size() == 1));
        Object o2 = cache.put(KEY_2, value, 2, TimeUnit.SECONDS);
        Assert.assertNull(o2);
        Object o3 = cache.get(KEY_2);
        Assert.assertNotNull(o3);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, Object> snapshot2 = cache.snapshot();
        Assert.assertTrue((snapshot2.size() == 1));
        Object o4 = cache.get(KEY_2);
        Assert.assertNull(o4);
    }

    @After
    public void tearDown() throws Exception {
        cache.evictAll();
    }
}