package libcore.base;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;

import java.util.Map;

/**
 * IScheduledLruCache
 * Created by dovsnier on 2019-07-09.
 */
@Scheduled
public interface IScheduledLruCache<K, V> extends IAbstractScheduledLruCache, ILruGenre<K, V> {

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key   the current key
     * @param value the current value
     * @return
     */
    V put(@NonNull K key, V value);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key      the current key
     * @param value    the current value
     * @param duration the duration
     * @param timeUnit {@link TimeUnit}
     * @return {@see V}
     */
    @Scheduled
    V put(@NonNull K key, V value, long duration, TimeUnit timeUnit);

    /**
     * Returns the value for {@code key} if it exists in the cache or can be
     * created by {@code #create}. If a value was returned, it is moved to the
     * head of the queue. This returns null if a value is not cached and cannot
     * be created.
     */
    V get(K key);


    /**
     * Removes the entry for {@code key} if it exists.
     *
     * @return the previous value mapped by {@code key}.
     */
    V remove(K key);

    /**
     * this returns the number of entries in the cache.
     * For all other caches, this returns the sum of
     * the sizes of the entries in this cache.
     */
    int size();

    /**
     * Returns a copy of the current contents of the cache, ordered from least
     * recently accessed to most recently accessed.
     */
    Map<K, V> snapshot();
}
