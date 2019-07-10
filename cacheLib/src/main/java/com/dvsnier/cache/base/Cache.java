package com.dvsnier.cache.base;

import android.content.Context;

import com.dvsnier.cache.transaction.ICacheTransaction;

import libcore.base.IDiskLruCache;
import libcore.base.ILruCache;

/**
 * Cache
 * Created by dovsnier on 2018/6/12.
 */
public class Cache extends AbstractCache {

    protected ILruCache lruCache;
    protected IDiskLruCache diskLruCache;

    public Cache() {
        super();
    }

    public Cache(Context context) {
        super(context);
    }

    public Cache(Context context, ICacheTransaction cacheTransaction) {
        super(context, cacheTransaction);
    }

    //<editor-fold desc="ICacheGenre">

    @Override
    public ILruCache<String, Object> getCache() {
        return lruCache;
    }

    @Override
    public void setCache(ILruCache<String, Object> lruCache) {
        this.lruCache = lruCache;
    }

    @Override
    public IDiskLruCache getDiskCache() {
        return diskLruCache;
    }

    @Override
    public void setDiskCache(IDiskLruCache diskLruCache) {
        this.diskLruCache = diskLruCache;
    }

    //</editor-fold>
}
