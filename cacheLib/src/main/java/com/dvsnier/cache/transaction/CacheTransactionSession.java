package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Internal;

import java.io.InputStream;

/**
 * CacheTransactionSession
 * Created by dovsnier on 2019-07-15.
 */
public abstract class CacheTransactionSession implements ICacheTransactionSession<CacheTransactionSession> {

    protected String alias;
    protected ICacheTransaction<CacheTransactionSession> internalCacheTransactionListener;

    @Override
    public CacheTransactionSession putString(@NonNull String type, @NonNull String key, String value) {
        return putString(key, value);
    }

    @Override
    public CacheTransactionSession putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream) {
        return putInputStream(key, inputStream);
    }

    @Override
    public CacheTransactionSession putObject(@NonNull String type, @NonNull String key, Object value) {
        return putObject(key, value);
    }

    @Override
    public String getString(@NonNull String type, @NonNull String key) {
        return getString(key);
    }

    @Override
    public InputStream getInputStream(@NonNull String type, @NonNull String key) {
        return getInputStream(key);
    }

    @Override
    public Object getObject(@NonNull String type, @NonNull String key) {
        return getObject(key);
    }

    @Override
    public CacheTransactionSession put(@NonNull String type, @NonNull String key, Object value) {
        return put(key, value);
    }

    @Override
    public Object get(@NonNull String type, @NonNull String key) {
        return get(key);
    }

    @Override
    public CacheTransactionSession remove(@NonNull String type, @NonNull String key) {
        return remove(key);
    }

    @Override
    public boolean commit(@NonNull String type) {
        return commit();
    }

    @Override
    public CacheTransactionSession putString(@NonNull String key, String value) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().put(key, value);
        }
        return this;
    }

    @Override
    public CacheTransactionSession putInputStream(@NonNull String key, InputStream inputStream) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().putInputStream(key, inputStream);
        }
        return this;
    }

    @Override
    public CacheTransactionSession putObject(@NonNull String key, Object value) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().putObject(key, value);
        }
        return this;
    }

    @Override
    public String getString(@NonNull String key) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().getString(key);
        }
        return null;
    }

    @Override
    public InputStream getInputStream(@NonNull String key) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().getInputStream(key);
        }
        return null;
    }

    @Override
    public Object getObject(@NonNull String key) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().getObject(key);
        }
        return null;
    }

    @Override
    public CacheTransactionSession put(@NonNull String key, Object value) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().put(key, value);
        }
        return this;
    }

    @Override
    public Object get(@NonNull String key) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().get(key);
        }
        return null;
    }

    @Override
    public CacheTransactionSession remove(@NonNull String key) {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().remove(key);
        }
        return this;
    }

    @Override
    public boolean commit() {
        if (null != getCacheTransactionListener()) {
            return getCacheTransactionListener().commit();
        }
        return false;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Internal
    protected ICacheTransaction<CacheTransactionSession> getCacheTransactionListener() {
        return internalCacheTransactionListener;
    }

    @Internal
    protected void setCacheTransactionListener(ICacheTransaction<CacheTransactionSession> cacheTransactionListener) {
        this.internalCacheTransactionListener = cacheTransactionListener;
    }

    @Override
    public String toString() {
        return "CacheTransactionSession{" +
                "alias='" + alias + '\'' +
                '}';
    }
}
