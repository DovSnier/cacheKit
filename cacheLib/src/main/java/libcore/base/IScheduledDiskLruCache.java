package libcore.base;

import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;

import java.io.IOException;

import libcore.schedule.ScheduleDiskLruCache;

/**
 * IScheduledDiskLruCache
 * Created by dovsnier on 2019-07-09.
 */
@Scheduled
public interface IScheduledDiskLruCache extends IAbstractScheduledLruCache, IDiskLruGenre {

    /**
     * Returns an editor for the entry named {@code key}, or null if another
     * edit is in progress.
     *
     * @return {@link ScheduleDiskLruCache.Editor}
     * @throws IOException
     */
    ScheduleDiskLruCache.Editor edit(String key) throws IOException;

    /**
     * Returns an editor for the entry named {@code key}, or null if another
     * edit is in progress.
     *
     * @param key      the current key
     * @param duration the duration
     * @param timeUnit {@link TimeUnit}
     * @return {@link ScheduleDiskLruCache.Editor}
     * @throws IOException
     */
    @Scheduled
    ScheduleDiskLruCache.Editor edit(String key, long duration, TimeUnit timeUnit) throws IOException;

    /**
     * Returns a snapshot of the entry named {@code key}, or null if it doesn't
     * exist is not currently readable. If a value is returned, it is moved to
     * the head of the LRU queue.
     */
    ScheduleDiskLruCache.Snapshot get(String key) throws IOException;

    /**
     * Force buffered operations to the filesystem.
     */
    void flush() throws IOException;

    /**
     * Drops the entry for {@code key} if it exists and can be removed. Entries
     * actively being edited cannot be removed.
     *
     * @return true if an entry was removed.
     */
    boolean remove(String key) throws IOException;
}
