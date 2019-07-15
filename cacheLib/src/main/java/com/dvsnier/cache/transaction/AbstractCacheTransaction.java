package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dvsnier.cache.base.ICacheGenre;
import com.dvsnier.cache.base.ITransactionSession;
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
public abstract class AbstractCacheTransaction implements ICacheTransaction<CacheTransactionSession>, ITransactionSession<CacheTransactionSession>, ICacheGenre, ITransactionAlias {

    protected Type type;
    protected ILruCache cache;
    protected IDiskLruCache diskCache;
    protected ICacheTransactionSession transactionSession;

    public AbstractCacheTransaction() {
        transactionSession = new CacheTransactionSession() {
        };
        ((CacheTransactionSession) transactionSession).setCacheTransactionListener(this);
    }

    public AbstractCacheTransaction(ICacheTransactionSession transactionSession) {
        this.transactionSession = transactionSession;
        ((CacheTransactionSession) transactionSession).setCacheTransactionListener(this);
    }

    public AbstractCacheTransaction(ILruCache cache, IDiskLruCache diskCache) {
        this.cache = cache;
        this.diskCache = diskCache;
        transactionSession = new CacheTransactionSession() {
        };
        ((CacheTransactionSession) transactionSession).setCacheTransactionListener(this);
    }

    //<editor-fold desc="ITransactionAlias">

    @Override
    public CacheTransactionSession getCacheTransaction(@NonNull Type type) {
        this.type = type;
        return getTransactionSession();
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
    //<editor-fold desc="ITransactionSession">

    @Nullable
    @Override
    public CacheTransactionSession getTransactionSession() {
        if (null != transactionSession && transactionSession instanceof CacheTransactionSession) {
            return (CacheTransactionSession) transactionSession;
        }
        return null;
    }

    @Override
    public void setTransactionSession(CacheTransactionSession transactionSession) {
        this.transactionSession = transactionSession;
    }
    //</editor-fold>
}
