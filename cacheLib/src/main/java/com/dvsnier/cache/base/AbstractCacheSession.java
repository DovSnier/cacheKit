package com.dvsnier.cache.base;

import android.content.Context;
import android.support.annotation.Nullable;

import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.annotation.LSM;
import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.config.IAlias;
import com.dvsnier.cache.infrastructure.Debug;
import com.dvsnier.cache.transaction.CacheTransaction;
import com.dvsnier.cache.transaction.CacheTransactionSession;
import com.dvsnier.cache.transaction.ICacheTransaction;
import com.dvsnier.cache.transaction.IGetCacheTransaction;
import com.dvsnier.cache.transaction.IGetCacheTransactionSession;
import com.dvsnier.cache.transaction.ISetCacheTransactionListener;
import com.dvsnier.cache.transaction.OnCacheTransactionListener;

/**
 * AbstractCacheSession
 * Created by dovsnier on 2019-07-02.
 */
public abstract class AbstractCacheSession implements ICacheSession, ICacheGenre, IAlias,
        IGetCacheTransactionSession<CacheTransactionSession>, IGetCacheTransaction,
        ISetCacheTransactionListener<OnCacheTransactionListener> {

    protected Context context;
    protected String alias;
    protected ICacheTransaction cacheTransaction;
    protected OnCacheTransactionListener onCacheTransactionListener;

    public AbstractCacheSession() {
        if (null == cacheTransaction) {
            cacheTransaction = new CacheTransaction();
        }
    }

    public AbstractCacheSession(Context context) {
        this.context = context;
        if (null == cacheTransaction) {
            cacheTransaction = new CacheTransaction();
        }
    }

    public AbstractCacheSession(Context context, ICacheTransaction cacheTransaction) {
        this.context = context;
        this.cacheTransaction = cacheTransaction;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Multiple
    @Override
    public String getAlias() {
        return alias;
    }

    @Multiple
    @Override
    public void setAlias(String alias) {
        this.alias = alias;
        if (null != getTransaction()) {
            getTransaction().setAlias(alias);
        }
    }

    @LSM
    @Nullable
    @Multiple
    @Override
    public CacheTransactionSession getTransaction() {
        if (getCacheTransaction() instanceof ITransactionSession) {
            //noinspection unchecked
            return ((ITransactionSession<CacheTransactionSession>) getCacheTransaction()).getTransactionSession();
        }
        return null;
    }

    @Override
    public ICacheSession getCacheSession() {
        return this;
    }

    @Override
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
                    getOnCacheTransactionListener().onCacheTransactionChanged(getAlias(), cacheTransaction);
                }
            }
    }

    public OnCacheTransactionListener getOnCacheTransactionListener() {
        return onCacheTransactionListener;
    }

    @Hide
    @Override
    public void setOnCacheTransactionListener(OnCacheTransactionListener onCacheTransactionListener) {
        this.onCacheTransactionListener = onCacheTransactionListener;
        Debug.d(String.format("the current cache engine(%s), an cache transaction listener has been set up.", getAlias()));
    }
}
