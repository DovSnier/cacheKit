package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dvsnier.cache.base.ICacheGenre;
import com.dvsnier.cache.base.ITransactionSession;
import com.dvsnier.cache.config.ITransactionAlias;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.Debug;

import libcore.base.IDiskLruGenre;
import libcore.base.ILruGenre;

/**
 * AbstractCacheTransaction
 * Created by dovsnier on 2019-07-12.
 */
public abstract class AbstractCacheTransaction implements ICacheGenre, ITransactionAlias,
        ICacheTransaction<CacheTransactionSession>, ICacheScheduledTransaction<CacheTransactionSession>,
        ITransactionSession<CacheTransactionSession>,
        ISetTransactionSessionChangeListener<OnTransactionSessionChangeListener> {

    protected Type type;
    protected ILruGenre cache;
    protected IDiskLruGenre diskCache;
    protected ICacheTransactionSession transactionSession;
    protected OnTransactionSessionChangeListener onTransactionSessionChangeListener;

    public AbstractCacheTransaction() {
        transactionSession = new CacheTransactionSession() {
        };
        ((CacheTransactionSession) transactionSession).setTransactionListener(this);
    }

    public AbstractCacheTransaction(ICacheTransactionSession transactionSession) {
        this.transactionSession = transactionSession;
        ((CacheTransactionSession) transactionSession).setTransactionListener(this);
    }

    public AbstractCacheTransaction(ILruGenre cache, IDiskLruGenre diskCache) {
        this.cache = cache;
        this.diskCache = diskCache;
        transactionSession = new CacheTransactionSession() {
        };
        ((CacheTransactionSession) transactionSession).setTransactionListener(this);
    }

    //<editor-fold desc="ITransactionAlias">

    @Override
    public CacheTransactionSession getCacheTransaction(@NonNull Type type) {
        this.type = type;
        return getTransactionSession();
    }

    //</editor-fold>
    //<editor-fold desc="ICacheGenre">

//    @Override
//    public ILruGenre<String, Object> getCache() {
//        //noinspection unchecked
//        return cache;
//    }

    @Override
    public void setCache(ILruGenre<String, Object> lruCache) {
        this.cache = lruCache;
    }

//    @Override
//    public IDiskLruGenre getDiskCache() {
//        return diskCache;
//    }

    @Override
    public void setDiskCache(IDiskLruGenre diskLruCache) {
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

    public OnTransactionSessionChangeListener getOnTransactionSessionChangeListener() {
        return onTransactionSessionChangeListener;
    }

    @Override
    public void setOnTransactionSessionChangeListener(OnTransactionSessionChangeListener onTransactionSessionChangeListener) {
        this.onTransactionSessionChangeListener = onTransactionSessionChangeListener;
        //noinspection ConstantConditions
        Debug.d(String.format("the current cache engine(%s), an cache transaction session change listener has been set up.", getTransactionSession().getAlias()));
    }

    protected boolean validateKey(@NonNull String key) {
        //noinspection ConstantConditions
        return null == key || " ".equals(key);
    }

    protected boolean validateValue(Object value) {
        return null == value;
    }

    protected boolean validateOnTransactionSessionChangeListener() {
        return null != getOnTransactionSessionChangeListener();
    }
}
