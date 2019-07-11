package com.dvsnier.cache.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.CacheStorage;

import java.io.File;
import java.io.IOException;

import libcore.base.IDiskLruCache;
import libcore.base.ILruCache;
import libcore.io.DiskLruCache;
import libcore.io.LruCache;

/**
 * Instantiate
 * Created by dovsnier on 2019-07-09.
 */
public class Instantiate implements ICacheEngine, Evictable {

    protected Context context;
    protected ILruCache lruCache;
    protected IDiskLruCache diskLruCache;

    public Instantiate() {
    }

    public Instantiate(Context context) {
        this.context = context;
    }

    @Override
    public void initialize(@NonNull Context context) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        int size = (int) (maxMemory / 8);
        lruCache = new LruCache<>(size);
        File cache = CacheStorage.INSTANCE().getDiskCacheDir(context, null);
        try {
            diskLruCache = DiskLruCache.open(cache, 1, 1, ICache.DEFAULT_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        int cacheMaxSizeOfMemory = cacheConfig.getCacheMaxSizeOfMemory();
        if (cacheMaxSizeOfMemory <= 0) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            cacheMaxSizeOfMemory = (int) (maxMemory / 8);
        }
        lruCache = new LruCache<>(cacheMaxSizeOfMemory);
        try {
            File cacheDirectory = cacheConfig.getCacheDirectory();
            int appVersion = cacheConfig.getAppVersion();
            int valueCount = cacheConfig.getValueCount();
            long cacheMaxSizeOfDisk = cacheConfig.getCacheMaxSizeOfDisk();
            diskLruCache = DiskLruCache.open(null != cacheDirectory ? cacheDirectory : TextUtils.isEmpty(cacheConfig.getUniqueName()) ?
                            CacheStorage.INSTANCE().getDiskCacheDir(context, null) : CacheStorage.INSTANCE().getDiskCacheDir(context, cacheConfig.getUniqueName()),
                    appVersion > 0 ? appVersion : 1,
                    valueCount > 0 ? valueCount : 1,
                    cacheMaxSizeOfDisk > 0 ? cacheMaxSizeOfDisk : ICache.DEFAULT_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (null != context) context = null;
        if (null != lruCache) lruCache = null;
        if (null != diskLruCache) {
            try {
                diskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            diskLruCache = null;
        }
    }

    @Override
    public boolean evict(@NonNull Type type) {
        switch (type) {
            case MEMORY:
                if (null != getLruCache()) {
                    getLruCache().evictAll();
                }
                return true;
            case DISK:
                if (null != getDiskLruCache()) {
                    getDiskLruCache().evictAll();
                }
                return true;
            case DEFAULT:
                evictAll();
                return true;
        }
        return false;
    }

    @Override
    public void evictAll() {
        if (null != getLruCache()) {
            getLruCache().evictAll();
        }
        if (null != getDiskLruCache()) {
            getDiskLruCache().evictAll();
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ILruCache getLruCache() {
        return lruCache;
    }

    private void setLruCache(ILruCache lruCache) {
        this.lruCache = lruCache;
    }

    public IDiskLruCache getDiskLruCache() {
        return diskLruCache;
    }

    private void setDiskLruCache(IDiskLruCache diskLruCache) {
        this.diskLruCache = diskLruCache;
    }
}
