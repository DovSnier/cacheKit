package com.dvsnier.cache.wrapper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.LSM;
import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.base.AbstractCacheSession;
import com.dvsnier.cache.base.CacheSession;
import com.dvsnier.cache.base.ICacheEngine;
import com.dvsnier.cache.base.ICacheGenre;
import com.dvsnier.cache.base.ICacheSession;
import com.dvsnier.cache.base.IGetInstantiate;
import com.dvsnier.cache.base.Instantiate;
import com.dvsnier.cache.config.IAlias;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.transaction.CacheTransactionSession;
import com.dvsnier.cache.transaction.IGetCacheTransactionSession;

/**
 * CacheWrapper
 * Created by dovsnier on 2019-07-02.
 */
public class CacheWrapper implements ICacheWrapper, IGetInstantiate {

    protected ICacheEngine instantiate;
    protected ICacheSession cacheSession;

    public CacheWrapper() {
        if (null == instantiate) {
            instantiate = new Instantiate();
        }
        cacheSession = new CacheSession();
    }

    public CacheWrapper(CacheSession cacheSession) {
        if (null == instantiate) {
            instantiate = new Instantiate();
        }
        this.cacheSession = cacheSession;
    }

    //<editor-fold desc="Closable">

    @Override
    public void close() {
        if (null != getInstantiate()) {
            getInstantiate().close();
        }
        if (null != instantiate) {
            instantiate = null;
        }
        if (null != cacheSession) {
            cacheSession = null;
        }
    }

    //</editor-fold>
    //<editor-fold desc="IInstantiate">

    @Override
    public void initialize(@NonNull Context context) {
        //noinspection ConstantConditions
        if (null == context) {
            throw new IllegalArgumentException("the Context object that can't be null.");
        }
        if (null != getInstantiate()) {
            getInstantiate().initialize(context);
            if (getInstantiate() instanceof IAlias && getCacheSession() instanceof IAlias) {
                ((IAlias) getCacheSession()).setAlias(((IAlias) getInstantiate()).getAlias());
            }
            if (getInstantiate() instanceof Instantiate) {
                if (getCacheSession() instanceof ICacheGenre) {
                    //noinspection unchecked
                    ((ICacheGenre) getCacheSession()).setCache(((Instantiate) getInstantiate()).getLruCache());
                    ((ICacheGenre) getCacheSession()).setDiskCache(((Instantiate) getInstantiate()).getDiskLruCache());
                }
            }
        }
        if (getCacheSession() instanceof AbstractCacheSession) {
            ((AbstractCacheSession) getCacheSession()).setOrScheduledCacheTransaction();
        }
    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        //noinspection ConstantConditions
        if (null == cacheConfig) {
            throw new IllegalArgumentException("the ICacheConfig object that can't be null.");
        }
        if (null == cacheConfig.getContext()) {
            throw new IllegalArgumentException("the Context object that can't be null.");
        }
        if (null != getInstantiate()) {
            getInstantiate().initialize(cacheConfig);
            if (getInstantiate() instanceof IAlias && getCacheSession() instanceof IAlias) {
                ((IAlias) getCacheSession()).setAlias(((IAlias) getInstantiate()).getAlias());
            }
            if (getInstantiate() instanceof Instantiate) {
                if (getInstantiate() instanceof Instantiate) {
                    if (getCacheSession() instanceof ICacheGenre) {
                        //noinspection unchecked
                        ((ICacheGenre) getCacheSession()).setCache(((Instantiate) getInstantiate()).getLruCache());
                        ((ICacheGenre) getCacheSession()).setDiskCache(((Instantiate) getInstantiate()).getDiskLruCache());
                    }
                }
            }
        }
        if (getCacheSession() instanceof AbstractCacheSession) {
            ((AbstractCacheSession) getCacheSession()).setOrScheduledCacheTransaction();
        }
    }

    //</editor-fold>
    //<editor-fold desc="IGetCacheTransaction">

    @LSM
    @Multiple
    @Override
    public CacheTransactionSession getTransaction() {
        if (null != getCacheSession() && getCacheSession() instanceof IGetCacheTransactionSession) {
//            return ((IGetCacheTransactionSession<CacheTransactionSimpleSession>) getCacheSession()).getTransaction();
            // TODO: 2019-07-12 兼容性处理CacheTransactionSession 和CacheTransactionSimpleSession 问题
            return (CacheTransactionSession) ((IGetCacheTransactionSession) getCacheSession()).getTransaction();
        }
        return null;
    }

    //</editor-fold>

    @Override
    public ICacheEngine getInstantiate() {
        return instantiate;
    }

    public void setInstantiate(ICacheEngine instantiate) {
        this.instantiate = instantiate;
    }

    public ICacheSession getCacheSession() {
        return cacheSession;
    }

    public void setCacheSession(ICacheSession cacheSession) {
        this.cacheSession = cacheSession;
    }
}
