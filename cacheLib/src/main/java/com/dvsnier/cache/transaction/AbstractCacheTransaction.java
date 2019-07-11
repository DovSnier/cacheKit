package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.base.ICacheGenre;
import com.dvsnier.cache.config.ITransactionAlias;
import com.dvsnier.cache.config.Type;

import libcore.base.IDiskLruCache;
import libcore.base.ILruCache;
import libcore.io.DiskLruCache;
import libcore.io.LruCache;

/**
 * AbstractCacheTransaction
 * Created by dovsnier on 2019-07-12.
 */
public abstract class AbstractCacheTransaction implements CacheTransactionSimpleSession, ICacheGenre, ITransactionAlias {

    protected Type type;
    protected ILruCache cache;
    protected IDiskLruCache diskCache;

    public AbstractCacheTransaction() {
    }

    public AbstractCacheTransaction(ILruCache cache, IDiskLruCache diskCache) {
        this.cache = cache;
        this.diskCache = diskCache;
    }

    //<editor-fold desc="ITransactionAlias">

    @Override
    public CacheTransactionSimpleSession getCacheTransaction(@NonNull Type type) {
        this.type = type;
        return this;
    }

    //</editor-fold>

    //<editor-fold desc="ICacheGenre">

    @Override
    public LruCache<String, Object> getCache() {
        //noinspection unchecked
        return (LruCache<String, Object>) cache;
    }

    @Override
    public void setCache(ILruCache<String, Object> lruCache) {
        this.cache = lruCache;
    }

    @Override
    public DiskLruCache getDiskCache() {
        return (DiskLruCache) diskCache;
    }

    @Override
    public void setDiskCache(IDiskLruCache diskLruCache) {
        this.diskCache = diskLruCache;
    }

    //</editor-fold>
}
