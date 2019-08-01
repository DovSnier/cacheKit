package com.dvsnier.cache.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dvsnier.cache.BuildConfig;
import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.config.IAlias;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.Type;

import libcore.base.IDiskLruGenre;
import libcore.base.ILruGenre;

/**
 * CacheEngineInstrument
 * Created by dovsnier on 2019-07-24.
 */
public class CacheEngineInstrument implements IEngineInstrument, IGetInstantiate {

    protected ICacheEngine instantiate;
    protected OnEngineInstrumentStatusListener onEngineInstrumentStatusListener;

    public CacheEngineInstrument() {
        if (null == instantiate) {
            instantiate = new Instantiate();
        }
    }

    public CacheEngineInstrument(String alias) {
        if (null == instantiate) {
            instantiate = new Instantiate(alias);
        }
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

        if (null != getOnEngineInstrumentStatusListener()) {
            getOnEngineInstrumentStatusListener().onClose();
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
        onSdkCallback(context);
        if (null != getInstantiate()) {
            getInstantiate().initialize(context);
            if (null != getOnEngineInstrumentStatusListener()) {
                getOnEngineInstrumentStatusListener().onInitialize(new ICacheConfig.Builder(context).create());
            }
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
        onSdkCallback(cacheConfig.getContext());
        if (null != getInstantiate()) {
            getInstantiate().initialize(cacheConfig);
            if (null != getOnEngineInstrumentStatusListener()) {
                getOnEngineInstrumentStatusListener().onInitialize(cacheConfig);
            }
        }
    }

    //</editor-fold>

    @Hide
    public boolean evict(@NonNull Type type) {
        if (null != getInstantiate() && getInstantiate() instanceof Instantiate) {
            ((Instantiate) getInstantiate()).evict(type);
            if (null != getOnEngineInstrumentStatusListener()) {
                getOnEngineInstrumentStatusListener().onEvict(type);
            }
            return true;
        }
        return false;
    }

    public void evictAll() {
        if (null != getInstantiate() && getInstantiate() instanceof Instantiate) {
            ((Instantiate) getInstantiate()).evictAll();
        }
    }

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

    @Override
    public String getAlias() {
        if (getInstantiate() instanceof IAlias) {
            return ((IAlias) getInstantiate()).getAlias();
        }
        return null;
    }

    @Override
    public void setAlias(String alias) {
        if (getInstantiate() instanceof IAlias) {
            ((IAlias) getInstantiate()).setAlias(alias);
        }
    }

    @Override
    public ICacheEngine getInstantiate() {
        return instantiate;
    }

    public void setInstantiate(ICacheEngine instantiate) {
        this.instantiate = instantiate;
    }

    public OnEngineInstrumentStatusListener getOnEngineInstrumentStatusListener() {
        return onEngineInstrumentStatusListener;
    }

    public void setOnEngineInstrumentStatusListener(OnEngineInstrumentStatusListener onEngineInstrumentStatusListener) {
        this.onEngineInstrumentStatusListener = onEngineInstrumentStatusListener;
    }

    public ILruGenre getLruCache() {
        if (getInstantiate() instanceof Instantiate) {
            return ((Instantiate) getInstantiate()).getLruCache();
        }
        return null;
    }

    public IDiskLruGenre getDiskLruCache() {
        if (getInstantiate() instanceof Instantiate) {
            return ((Instantiate) getInstantiate()).getDiskLruCache();
        }
        return null;
    }

    @Nullable
    public CacheGenre getCacheGenre() {
        if (null != getInstantiate() && getInstantiate() instanceof Instantiate) {
            return ((Instantiate) getInstantiate()).getCacheGenre();
        }
        return null;
    }
}
