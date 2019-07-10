package com.dvsnier.cache.wrapper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.dvsnier.cache.BuildConfig;
import com.dvsnier.cache.base.AbstractCache;
import com.dvsnier.cache.base.Cache;
import com.dvsnier.cache.base.ICache;
import com.dvsnier.cache.base.ICacheEngine;
import com.dvsnier.cache.base.ICacheGenre;
import com.dvsnier.cache.base.Instantiate;
import com.dvsnier.cache.config.ICacheAPI;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.transaction.ICacheTransaction;
import com.dvsnier.cache.transaction.IGetCacheTransaction;

/**
 * CacheWrapper
 * Created by dovsnier on 2019-07-02.
 */
public class CacheWrapper implements ICacheWrapper, ICacheAPI {

    protected ICacheEngine instantiate;
    protected ICache cache;

    public CacheWrapper() {
        if (null == instantiate) {
            instantiate = new Instantiate();
        }
        cache = new Cache();
    }

    public CacheWrapper(Cache cache) {
        if (null == instantiate) {
            instantiate = new Instantiate();
        }
        this.cache = cache;
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
        if (null != cache) {
            cache = null;
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
            if (getInstantiate() instanceof Instantiate) {
                if (getCache() instanceof ICacheGenre) {
                    //noinspection unchecked
                    ((ICacheGenre) getCache()).setCache(((Instantiate) getInstantiate()).getLruCache());
                    ((ICacheGenre) getCache()).setDiskCache(((Instantiate) getInstantiate()).getDiskLruCache());
                }
            }
        }
        if (getCache() instanceof AbstractCache) {
            ((AbstractCache) getCache()).setOrScheduledCacheTransaction();
        }
        onSdkCallback(context);
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
            if (getInstantiate() instanceof Instantiate) {
                if (getInstantiate() instanceof Instantiate) {
                    if (getCache() instanceof ICacheGenre) {
                        //noinspection unchecked
                        ((ICacheGenre) getCache()).setCache(((Instantiate) getInstantiate()).getLruCache());
                        ((ICacheGenre) getCache()).setDiskCache(((Instantiate) getInstantiate()).getDiskLruCache());
                    }
                }
            }
        }
        if (getCache() instanceof AbstractCache) {
            ((AbstractCache) getCache()).setOrScheduledCacheTransaction();
        }
        onSdkCallback(cacheConfig.getContext());
    }

    //</editor-fold>
    //<editor-fold desc="ICacheAPI">

    @SuppressLint("ApplySharedPref")
    @Override
    public void onSdkCallback(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SDK_FILE_NAME, Context.MODE_PRIVATE);
        if (null != sharedPreferences) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            if (null != edit) {
                edit.putString(SDK_VERSION_KEY, BuildConfig.cache_sdk_version);
                edit.commit();
            }
        }
    }

    //</editor-fold>
    //<editor-fold desc="IGetCacheTransaction">

    @Override
    public ICacheTransaction getTransaction() {
        if (null != getCache() && getCache() instanceof IGetCacheTransaction) {
            return ((IGetCacheTransaction) getCache()).getTransaction();
        }
        return null;
    }

    //</editor-fold>


    public ICacheEngine getInstantiate() {
        return instantiate;
    }

    public void setInstantiate(ICacheEngine instantiate) {
        this.instantiate = instantiate;
    }

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }
}
