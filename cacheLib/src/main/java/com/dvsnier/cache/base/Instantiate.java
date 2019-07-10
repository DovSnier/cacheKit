package com.dvsnier.cache.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.infrastructure.CacheUtil;

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
public class Instantiate implements ICacheEngine {

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
        File cache = CacheUtil.getInstance().getDiskCacheDir(context, null);
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
            diskLruCache = DiskLruCache.open(null != cacheDirectory ? cacheDirectory : CacheUtil.getInstance().getDiskCacheDir(context, null),
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
