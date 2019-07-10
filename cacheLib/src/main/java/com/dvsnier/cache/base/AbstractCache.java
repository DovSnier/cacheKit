package com.dvsnier.cache.base;

import android.content.Context;

import com.dvsnier.cache.transaction.CacheTransaction;
import com.dvsnier.cache.transaction.ICacheTransaction;
import com.dvsnier.cache.transaction.OnCacheTransactionListener;

/**
 * AbstractCache
 * Created by dovsnier on 2019-07-02.
 */
public abstract class AbstractCache implements ICache, ICacheGenre {

    protected Context context;
    protected ICacheTransaction cacheTransaction;
    protected OnCacheTransactionListener onCacheTransactionListener;

    public AbstractCache() {
        if (null == cacheTransaction) {
            cacheTransaction = new CacheTransaction();
        }
    }

    public AbstractCache(Context context) {
        this.context = context;
        if (null == cacheTransaction) {
            cacheTransaction = new CacheTransaction();
        }
    }

    public AbstractCache(Context context, ICacheTransaction cacheTransaction) {
        this.context = context;
        this.cacheTransaction = cacheTransaction;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ICacheTransaction getCacheTransaction() {
        return cacheTransaction;
    }

    public void setCacheTransaction(ICacheTransaction cacheTransaction) {
        this.cacheTransaction = cacheTransaction;
    }

    /**
     * the binding cache and cache transaction association
     */
    public final void setOrScheduledCacheTransaction() {
        if (null != getCacheTransaction())
            if (getCacheTransaction() instanceof ICacheGenre) {
                ((ICacheGenre) getCacheTransaction()).setCache(getCache());
                ((ICacheGenre) getCacheTransaction()).setDiskCache(getDiskCache());
                if (null != getOnCacheTransactionListener()) {
                    getOnCacheTransactionListener().onCacheTransactionChanged(cacheTransaction);
                }
            }
    }

    public OnCacheTransactionListener getOnCacheTransactionListener() {
        return onCacheTransactionListener;
    }

    public void setOnCacheTransactionListener(OnCacheTransactionListener onCacheTransactionListener) {
        this.onCacheTransactionListener = onCacheTransactionListener;
    }
}
