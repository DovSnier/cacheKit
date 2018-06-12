package com.dvsnier.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.transaction.CacheTransaction;
import com.dvsnier.cache.transaction.ICacheTransaction;
import com.dvsnier.cache.transaction.OnCacheTransactionListener;
import com.dvsnier.cache.utils.CacheUtil;

import java.io.File;
import java.io.IOException;

import libcore.io.DiskLruCache;
import libcore.io.LruCache;

/**
 * Created by dovsnier on 2018/6/12.
 */

public class Cache implements ICache, ICacheGenre {

    private Context context;
    private LruCache<String, Object> lruCache;
    private DiskLruCache diskLruCache;
    private ICacheTransaction cacheTransaction;
    private OnCacheTransactionListener onCacheTransactionListener;

    public Cache() {
        if (null == cacheTransaction) {
            cacheTransaction = new CacheTransaction();
        }
    }

    public Cache(@NonNull Context context) {
        this.context = context;
    }

    public Cache(@NonNull Context context, ICacheTransaction cacheTransaction) {
        this.context = context;
        this.cacheTransaction = cacheTransaction;
        setCacheTransaction();
    }

    //<editor-fold desc="ICache">

    @Override
    public void initialize(@NonNull Context context) {
        this.context = context;
        //noinspection ConstantConditions
        if (null == context) {
            throw new IllegalArgumentException("Context object that can't be null.");
        }
        long maxMemory = Runtime.getRuntime().maxMemory();
        int size = (int) (maxMemory / 8);
        lruCache = new LruCache<>(size);
        File cache = CacheUtil.getInstance().getDiskCacheDir(context, null);
        try {
            diskLruCache = DiskLruCache.open(cache, 1, 1, DEFAULT_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCacheTransaction();
    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        this.context = cacheConfig.getContext();
        //noinspection ConstantConditions
        if (null == context) {
            throw new IllegalArgumentException("Context object that can't be null.");
        }
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
            diskLruCache = DiskLruCache.open(null != cacheDirectory ? cacheDirectory : CacheUtil.getInstance().getDiskCacheDir(context, null), appVersion > 0 ? appVersion : 1, valueCount > 0 ? valueCount : 1, cacheMaxSizeOfDisk > 0 ? cacheMaxSizeOfDisk : DEFAULT_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCacheTransaction();
    }

    @Override
    public void close() {
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

    //</editor-fold>
    //<editor-fold desc="ICacheGenre">

    @Override
    public LruCache<String, Object> getCache() {
        return lruCache;
    }

    @Override
    public DiskLruCache getDiskCache() {
        return diskLruCache;
    }

    //</editor-fold>

    @NonNull
    public ICacheTransaction getCacheTransaction() {
        return cacheTransaction;
    }

    private void setCacheTransaction() {
        if (null != cacheTransaction)
            if (cacheTransaction instanceof CacheTransaction) {
                ((CacheTransaction) cacheTransaction).setCache(getCache());
                ((CacheTransaction) cacheTransaction).setDiskCache(getDiskCache());
                if (null != onCacheTransactionListener) {
                    onCacheTransactionListener.onCacheTransactionChanged(cacheTransaction);
                }
            }
    }

    protected void setCacheTransaction(ICacheTransaction cacheTransaction) {
        this.cacheTransaction = cacheTransaction;
        setCacheTransaction();
    }

    public OnCacheTransactionListener getOnCacheTransactionListener() {
        return onCacheTransactionListener;
    }

    public void setOnCacheTransactionListener(OnCacheTransactionListener onCacheTransactionListener) {
        this.onCacheTransactionListener = onCacheTransactionListener;
    }
}
