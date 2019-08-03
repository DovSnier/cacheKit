package com.dvsnier.cache.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dvsnier.cache.R;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.CacheStorage;
import com.dvsnier.cache.infrastructure.Debug;

import java.io.File;
import java.io.IOException;

import libcore.base.IBaseCache;
import libcore.base.IDiskLruGenre;
import libcore.base.ILruGenre;
import libcore.io.DiskLruCache;
import libcore.io.LruCache;
import libcore.schedule.ScheduleDiskLruCache;
import libcore.schedule.ScheduledLruCache;

/**
 * CacheGenre
 * Created by dovsnier on 2019-07-30.
 */
public abstract class CacheGenre {

    public static class DEFAULT extends CacheGenre {
        @Override
        public void initialize(@NonNull Context context) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            int size = (int) (maxMemory / IBaseCache.DEFAULT_MAX_DENOMINATOR_OF_MEMORY);
            lruCache = new LruCache<>(size);
            File cache = CacheStorage.INSTANCE().getDiskCacheDir(context, context.getString(R.string.unique_name));
            Debug.d(String.format("the current cache engine(%s - memory:           %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(size, CacheStorage.SCU.M)));
            try {
                diskLruCache = DiskLruCache.open(cache, IBaseCache.ONE, IBaseCache.ONE, IBaseCache.DEFAULT_MAX_SIZE);
                Debug.d(String.format("the current cache engine(%s - disk directory:   %s)", getAlias(), cache.getAbsolutePath()));
                Debug.d(String.format("the current cache engine(%s - disk version:     %s)", getAlias(), IBaseCache.ONE));
                Debug.d(String.format("the current cache engine(%s - disk value count: %s)", getAlias(), IBaseCache.ONE));
                Debug.d(String.format("the current cache engine(%s - disk max size:    %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(IBaseCache.DEFAULT_MAX_SIZE, CacheStorage.SCU.M)));
            } catch (IOException e) {
                e.printStackTrace();
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache initialization", getAlias(), e.getMessage()));
            }
        }

        @Override
        public void initialize(@NonNull ICacheConfig cacheConfig) {
            int cacheMaxSizeOfMemory = cacheConfig.getCacheMaxSizeOfMemory();
            if (cacheMaxSizeOfMemory <= IBaseCache.DEFAULT) {
                long maxMemory = Runtime.getRuntime().maxMemory();
                cacheMaxSizeOfMemory = (int) (maxMemory / IBaseCache.DEFAULT_MAX_DENOMINATOR_OF_MEMORY);
            }
            lruCache = new LruCache<>(cacheMaxSizeOfMemory);
            File cacheDirectory = cacheConfig.getCacheDirectory();
            int appVersion = cacheConfig.getAppVersion();
            int valueCount = cacheConfig.getValueCount();
            long cacheMaxSizeOfDisk = cacheConfig.getCacheMaxSizeOfDisk();
            String uniqueName = cacheConfig.getUniqueName();
            if (!TextUtils.isEmpty(uniqueName) && !uniqueName.equals(getAlias())) {
                setAlias(uniqueName);
            }
            File directory = null != cacheDirectory ? cacheDirectory : TextUtils.isEmpty(uniqueName) ?
                    CacheStorage.INSTANCE().getDiskCacheDir(cacheConfig.getContext(), null) : CacheStorage.INSTANCE().getDiskCacheDir(cacheConfig.getContext(), uniqueName);
            Debug.d(String.format("the current cache engine(%s - memory:           %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(cacheMaxSizeOfMemory, CacheStorage.SCU.M)));
            int version = appVersion > IBaseCache.DEFAULT ? appVersion : IBaseCache.ONE;
            int count = valueCount > IBaseCache.DEFAULT ? valueCount : IBaseCache.ONE;
            long maxSize = cacheMaxSizeOfDisk > IBaseCache.DEFAULT ? cacheMaxSizeOfDisk : IBaseCache.DEFAULT_MAX_SIZE;
            try {
                diskLruCache = DiskLruCache.open(directory, version, count, maxSize);
                Debug.d(String.format("the current cache engine(%s - disk directory:   %s)", getAlias(), directory.getAbsolutePath()));
                Debug.d(String.format("the current cache engine(%s - disk version:     %s)", getAlias(), version));
                Debug.d(String.format("the current cache engine(%s - disk value count: %s)", getAlias(), count));
                Debug.d(String.format("the current cache engine(%s - disk max size:    %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(maxSize, CacheStorage.SCU.M)));
            } catch (IOException e) {
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache initialization", getAlias(), e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    public static class SCHEDULED extends CacheGenre {
        @Override
        public void initialize(@NonNull Context context) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            int size = (int) (maxMemory / IBaseCache.DEFAULT_MAX_DENOMINATOR_OF_MEMORY);
            lruCache = new ScheduledLruCache(size);
            File cache = CacheStorage.INSTANCE().getDiskCacheDir(context, context.getString(R.string.unique_name));
            Debug.d(String.format("the current cache engine(%s - memory:           %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(size, CacheStorage.SCU.M)));
            try {
                diskLruCache = ScheduleDiskLruCache.open(cache, IBaseCache.ONE, IBaseCache.ONE, IBaseCache.DEFAULT_MAX_SIZE);
                Debug.d(String.format("the current cache engine(%s - disk directory:   %s)", getAlias(), cache.getAbsolutePath()));
                Debug.d(String.format("the current cache engine(%s - disk version:     %s)", getAlias(), IBaseCache.ONE));
                Debug.d(String.format("the current cache engine(%s - disk value count: %s)", getAlias(), IBaseCache.ONE));
                Debug.d(String.format("the current cache engine(%s - disk max size:    %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(IBaseCache.DEFAULT_MAX_SIZE, CacheStorage.SCU.M)));
            } catch (IOException e) {
                e.printStackTrace();
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache initialization", getAlias(), e.getMessage()));
            }
        }

        @Override
        public void initialize(@NonNull ICacheConfig cacheConfig) {
            int cacheMaxSizeOfMemory = cacheConfig.getCacheMaxSizeOfMemory();
            if (cacheMaxSizeOfMemory <= IBaseCache.DEFAULT) {
                long maxMemory = Runtime.getRuntime().maxMemory();
                cacheMaxSizeOfMemory = (int) (maxMemory / IBaseCache.DEFAULT_MAX_DENOMINATOR_OF_MEMORY);
            }
            lruCache = new ScheduledLruCache(cacheMaxSizeOfMemory);
            File cacheDirectory = cacheConfig.getCacheDirectory();
            int appVersion = cacheConfig.getAppVersion();
            int valueCount = cacheConfig.getValueCount();
            long cacheMaxSizeOfDisk = cacheConfig.getCacheMaxSizeOfDisk();
            String uniqueName = cacheConfig.getUniqueName();
            if (!TextUtils.isEmpty(uniqueName) && !uniqueName.equals(getAlias())) {
                setAlias(uniqueName);
            }
            File directory = null != cacheDirectory ? cacheDirectory : TextUtils.isEmpty(uniqueName) ?
                    CacheStorage.INSTANCE().getDiskCacheDir(cacheConfig.getContext(), null) : CacheStorage.INSTANCE().getDiskCacheDir(cacheConfig.getContext(), uniqueName);
            Debug.d(String.format("the current cache engine(%s - memory:           %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(cacheMaxSizeOfMemory, CacheStorage.SCU.M)));
            int version = appVersion > IBaseCache.DEFAULT ? appVersion : IBaseCache.ONE;
            int count = valueCount > IBaseCache.DEFAULT ? valueCount : IBaseCache.ONE;
            long maxSize = cacheMaxSizeOfDisk > IBaseCache.DEFAULT ? cacheMaxSizeOfDisk : IBaseCache.DEFAULT_MAX_SIZE;
            try {
                diskLruCache = ScheduleDiskLruCache.open(directory, version, count, maxSize);
                Debug.d(String.format("the current cache engine(%s - disk directory:   %s)", getAlias(), directory.getAbsolutePath()));
                Debug.d(String.format("the current cache engine(%s - disk version:     %s)", getAlias(), version));
                Debug.d(String.format("the current cache engine(%s - disk value count: %s)", getAlias(), count));
                Debug.d(String.format("the current cache engine(%s - disk max size:    %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(maxSize, CacheStorage.SCU.M)));
            } catch (IOException e) {
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache initialization", getAlias(), e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    protected String alias;
    protected ILruGenre lruCache;
    protected IDiskLruGenre diskLruCache;


    public ILruGenre getLruCache() {
        return lruCache;
    }

    protected void setLruCache(ILruGenre lruCache) {
        this.lruCache = lruCache;
    }

    public IDiskLruGenre getDiskLruCache() {
        return diskLruCache;
    }

    protected void setDiskLruCache(IDiskLruGenre diskLruCache) {
        this.diskLruCache = diskLruCache;
    }

    public String getAlias() {
        return alias;
    }

    public CacheGenre setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public void initialize(@NonNull Context context) {
        throw new AbstractMethodError();
    }

    public void initialize(@NonNull ICacheConfig cacheConfig) {
        throw new AbstractMethodError();
    }

    public void close() {
        if (null != lruCache) lruCache = null;
        if (null != diskLruCache) {
            try {
                diskLruCache.close();
            } catch (IOException e) {
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache closed", getAlias(), e.getMessage()));
                e.printStackTrace();
            }
            diskLruCache = null;
        }
    }

    public boolean evict(@NonNull Type type) {
        switch (type) {
            case MEMORY:
                if (null != getLruCache()) {
                    getLruCache().evictAll();
                    Debug.w(String.format("the current cache engine(%s - %s) is evicted.", getAlias(), type.toString().toLowerCase()));
                }
                setLruCache(null);
                return true;
            case DISK:
                if (null != getDiskLruCache()) {
                    getDiskLruCache().evictAll();
                    Debug.w(String.format("the current cache engine(%s - %s) is evicted.", getAlias(), type.toString().toLowerCase()));
                }
                setDiskLruCache(null);
                return true;
            case DEFAULT:
                evictAll();
                return true;
        }
        return false;
    }

    public void evictAll() {
        if (null != getLruCache()) {
            getLruCache().evictAll();
        }
        if (null != getDiskLruCache()) {
            getDiskLruCache().evictAll();
        }
        setLruCache(null);
        setDiskLruCache(null);
        Debug.i(String.format("the current cache engine(%s) is evicted.", getAlias()));
    }

}
