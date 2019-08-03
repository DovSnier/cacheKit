package com.dvsnier.cache.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.annotation.Improvement;
import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.config.IAlias;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.ICacheEngineConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.Debug;

import libcore.base.IDiskLruGenre;
import libcore.base.ILruGenre;

/**
 * Instantiate
 * Created by dovsnier on 2019-07-09.
 */
public class Instantiate implements ICacheEngine, ICacheEngineConfig, IAlias, Evictable {

    protected Context context;
    protected String alias;
    @Improvement(value = {"instantiate"})
    protected boolean instantiate;
    @Improvement(value = {"close"})
    protected boolean close;
    protected CacheGenre cacheGenre;

    public Instantiate() {
        setAlias(IType.TYPE_NONE);
    }

    public Instantiate(String alias) {
        setAlias(alias);
    }

    public Instantiate(Context context) {
        setAlias(IType.TYPE_NONE);
        setContext(context);
    }

    @Override
    public void initialize(@NonNull Context context) {
        if (isInstantiate()) {
            Debug.w(String.format("the current cache engine(%s) has been initialized. do not repeat the initialization operation.", getAlias()));
            return;
        } else {
            setContext(context);
            cacheGenre = new CacheGenre.DEFAULT();
            cacheGenre.setAlias(getAlias()).initialize(context);
            setInstantiate(true);
        }
    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        if (isInstantiate()) {
            Debug.w(String.format("the current cache engine(%s) has been initialized. do not repeat the initialization operation.", getAlias()));
            return;
        } else {
            setContext(cacheConfig.getContext());
            cacheGenre = cacheConfig.getCacheGenre();
            if (null == cacheGenre) cacheGenre = new CacheGenre.DEFAULT();
            cacheGenre.setAlias(reAlias(TextUtils.isEmpty(cacheConfig.getUniqueName()) ? getAlias() : cacheConfig.getUniqueName())).initialize(cacheConfig);
            setInstantiate(true);
        }
    }

    @Override
    public void close() {
        if (null != context) context = null;
        if (null != cacheGenre) cacheGenre.close();
        instantiate = false;
        close = true;
        cacheGenre = null;
        Debug.i(String.format("the current cache engine(%s) is closed.", getAlias()));
    }

    @Override
    public boolean evict(@NonNull Type type) {
        if (null != cacheGenre) return cacheGenre.evict(type);
        return false;
    }

    @Override
    public void evictAll() {
        if (null != cacheGenre) {
            cacheGenre.evictAll();
        }
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
        if (null != cacheGenre) cacheGenre.setAlias(alias);
    }

    public String reAlias(String alias) {
        setAlias(alias);
        return alias;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Improvement
    @Override
    public boolean isInstantiate() {
        return instantiate;
    }

    @Improvement
    @Override
    public void setInstantiate(boolean instantiate) {
        this.instantiate = instantiate;
        if (instantiate) {
            setClose(false);
        }
    }

    @Improvement
    @Override
    public boolean isClose() {
        return close;
    }

    @Improvement
    @Hide
    protected void setClose(boolean close) {
        this.close = close;
    }

    @Nullable
    public ILruGenre getLruCache() {
        if (null != cacheGenre) return cacheGenre.getLruCache();
        return null;
    }

    @Nullable
    public IDiskLruGenre getDiskLruCache() {
        if (null != cacheGenre) return cacheGenre.getDiskLruCache();
        return null;
    }

    public CacheGenre getCacheGenre() {
        return cacheGenre;
    }
}
