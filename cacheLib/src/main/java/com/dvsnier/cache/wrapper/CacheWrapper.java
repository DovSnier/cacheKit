package com.dvsnier.cache.wrapper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.annotation.LSM;
import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.base.CacheEngineInstrument;
import com.dvsnier.cache.base.CacheSession;
import com.dvsnier.cache.base.ICacheSession;
import com.dvsnier.cache.base.IEngineInstrument;
import com.dvsnier.cache.base.OnEngineInstrumentStatusListener;
import com.dvsnier.cache.config.IAlias;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.transaction.CacheTransactionSession;
import com.dvsnier.cache.transaction.IGetCacheTransaction;
import com.dvsnier.cache.transaction.IGetCacheTransactionSession;
import com.dvsnier.cache.transaction.ISetCacheTransactionListener;
import com.dvsnier.cache.transaction.ISetTransactionSessionChangeListener;
import com.dvsnier.cache.transaction.OnCacheTransactionListener;
import com.dvsnier.cache.transaction.OnTransactionSessionChangeListener;

/**
 * CacheWrapper
 * Created by dovsnier on 2019-07-02.
 */
public class CacheWrapper implements ICacheWrapper, OnEngineInstrumentStatusListener {

    protected IEngineInstrument engineInstrument;
    protected ICacheSession cacheSession;

    public CacheWrapper() {
        engineInstrument = new CacheEngineInstrument();
        ((CacheEngineInstrument) engineInstrument).setOnEngineInstrumentStatusListener(this);
        cacheSession = new CacheSession();
    }

    public CacheWrapper(String alias) {
        if (TextUtils.isEmpty(alias)) {
            throw new IllegalArgumentException("the alias parameter cannot be null.");
        }
        engineInstrument = new CacheEngineInstrument(alias);
        ((CacheEngineInstrument) engineInstrument).setOnEngineInstrumentStatusListener(this);
        cacheSession = new CacheSession();
    }

    public CacheWrapper(CacheSession cacheSession) {
        engineInstrument = new CacheEngineInstrument();
        ((CacheEngineInstrument) engineInstrument).setOnEngineInstrumentStatusListener(this);
        this.cacheSession = cacheSession;
    }

    @Override
    public void close() {
        getEngineInstrument().close();
    }

    public void initialize(@NonNull Context context) {
        getEngineInstrument().initialize(context);
    }

    public void initialize(@NonNull ICacheConfig cacheConfig) {
        getEngineInstrument().initialize(cacheConfig);
    }

    //<editor-fold desc="OnEngineInstrumentStatusListener">

    @Override
    public void onInitialize(@NonNull ICacheConfig cacheConfig) {
        if (null != getEngineInstrument()) {
            // 1. to set alias
            if (getCacheSession() instanceof IAlias) {
                ((IAlias) getCacheSession()).setAlias(getEngineInstrument().getAlias());
            }
            // 2. to association caching engine
            if (getCacheSession() instanceof CacheSession) {
                ((CacheSession) getCacheSession()).associationCachingEngine(getEngineInstrument());
            }
        }
    }

    @Override
    public void onClose() {
        if (null != engineInstrument) {
            if (engineInstrument instanceof CacheEngineInstrument) {
                ((CacheEngineInstrument) engineInstrument).setOnEngineInstrumentStatusListener(null);
            }
            engineInstrument = null;
        }
        if (null != cacheSession) {
            cacheSession = null;
        }
    }

    //</editor-fold>
    //<editor-fold desc="IGetCacheTransaction">

    @LSM
    @Multiple
    @Nullable
    @Override
    public CacheTransactionSession getTransaction() {
        if (null != getCacheSession() && getCacheSession() instanceof IGetCacheTransactionSession) {
            //noinspection unchecked
            return ((IGetCacheTransactionSession<CacheTransactionSession>) getCacheSession()).getTransaction();
        }
        return null;
    }

    //</editor-fold>

    @Hide
    protected IEngineInstrument getEngineInstrument() {
        return engineInstrument;
    }

    @Override
    public ICacheSession getCacheSession() {
        return cacheSession;
    }

    public void setCacheSession(ICacheSession cacheSession) {
        this.cacheSession = cacheSession;
    }

    public String getAlias() {
        return getEngineInstrument().getAlias();
    }

    /**
     * the setting up cached session listening
     *
     * @param onCacheTransactionListener {@link OnCacheTransactionListener}
     * @deprecated
     */
    @Hide
    public void setOnCacheTransactionListener(OnCacheTransactionListener onCacheTransactionListener) {
        if (getCacheSession() instanceof ISetCacheTransactionListener) {
            //noinspection unchecked
            ((ISetCacheTransactionListener<OnCacheTransactionListener>) getCacheSession()).setOnCacheTransactionListener(onCacheTransactionListener);
        }
    }

    /**
     * the setting up cached session listening
     *
     * @param onTransactionSessionChangeListener {@link OnTransactionSessionChangeListener}
     */
    public void setOnTransactionSessionChangeListener(OnTransactionSessionChangeListener onTransactionSessionChangeListener) {
        if (getCacheSession() instanceof IGetCacheTransaction) {
            if (((IGetCacheTransaction) getCacheSession()).getCacheTransaction() instanceof ISetTransactionSessionChangeListener) {
                //noinspection unchecked
                ((ISetTransactionSessionChangeListener<OnTransactionSessionChangeListener>) ((IGetCacheTransaction) getCacheSession()).getCacheTransaction()).setOnTransactionSessionChangeListener(onTransactionSessionChangeListener);
            }
        }
    }
}
