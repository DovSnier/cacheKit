package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;

import java.io.InputStream;

/**
 * CacheTransactionSession
 * Created by dovsnier on 2019-07-15.
 */
public abstract class CacheTransactionSession extends AbstractCacheTransactionSession<CacheTransactionSession> {

    @Multiple
    @Scheduled
    @Override
    public CacheTransactionSession putString(@NonNull String type, @NonNull String key, String value, long duration, TimeUnit timeUnit) {
        return putString(key, value, duration, timeUnit);
    }

    @Multiple
    @Scheduled
    @Override
    public CacheTransactionSession putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream, long duration, TimeUnit timeUnit) {
        return putInputStream(key, inputStream, duration, timeUnit);
    }

    @Multiple
    @Scheduled
    @Override
    public CacheTransactionSession putObject(@NonNull String type, @NonNull String key, Object value, long duration, TimeUnit timeUnit) {
        return putObject(key, value, duration, timeUnit);
    }

    @Multiple
    @Scheduled
    @Override
    public CacheTransactionSession put(@NonNull String type, @NonNull String key, Object value, long duration, TimeUnit timeUnit) {
        return put(key, value, duration, timeUnit);
    }

    @Multiple
    @Override
    public CacheTransactionSession putString(@NonNull String type, @NonNull String key, String value) {
        return putString(key, value);
    }

    @Multiple
    @Override
    public CacheTransactionSession putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream) {
        return putInputStream(key, inputStream);
    }

    @Multiple
    @Override
    public CacheTransactionSession putObject(@NonNull String type, @NonNull String key, Object value) {
        return putObject(key, value);
    }

    @Multiple
    @Override
    public String getString(@NonNull String type, @NonNull String key) {
        return getString(key);
    }

    @Multiple
    @Override
    public InputStream getInputStream(@NonNull String type, @NonNull String key) {
        return getInputStream(key);
    }

    @Multiple
    @Override
    public Object getObject(@NonNull String type, @NonNull String key) {
        return getObject(key);
    }

    @Multiple
    @Override
    public CacheTransactionSession put(@NonNull String type, @NonNull String key, Object value) {
        return put(key, value);
    }

    @Multiple
    @Override
    public Object get(@NonNull String type, @NonNull String key) {
        return get(key);
    }

    @Multiple
    @Override
    public CacheTransactionSession remove(@NonNull String type, @NonNull String key) {
        return remove(key);
    }

    @Multiple
    @Override
    public boolean commit(@NonNull String type) {
        return commit();
    }

    @Scheduled
    @Override
    public CacheTransactionSession putString(@NonNull String key, String value, long duration, TimeUnit timeUnit) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheScheduledTransaction) {
            return (CacheTransactionSession) ((ICacheScheduledTransaction) getTransactionListener()).putString(key, value, duration, timeUnit);
        }
        return this;
    }

    @Scheduled
    @Override
    public CacheTransactionSession putInputStream(@NonNull String key, InputStream inputStream, long duration, TimeUnit timeUnit) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheScheduledTransaction) {
            return (CacheTransactionSession) ((ICacheScheduledTransaction) getTransactionListener()).putInputStream(key, inputStream, duration, timeUnit);
        }
        return this;
    }

    @Scheduled
    @Override
    public CacheTransactionSession putObject(@NonNull String key, Object value, long duration, TimeUnit timeUnit) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheScheduledTransaction) {
            return (CacheTransactionSession) ((ICacheScheduledTransaction) getTransactionListener()).putObject(key, value, duration, timeUnit);
        }
        return this;
    }

    @Scheduled
    @Override
    public CacheTransactionSession put(@NonNull String key, Object value, long duration, TimeUnit timeUnit) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheScheduledTransaction) {
            return (CacheTransactionSession) ((ICacheScheduledTransaction) getTransactionListener()).put(key, value, duration, timeUnit);
        }
        return this;
    }

    @Override
    public CacheTransactionSession putString(@NonNull String key, String value) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheTransaction) {
            return (CacheTransactionSession) ((ICacheTransaction) getTransactionListener()).putString(key, value);
        }
        return this;
    }

    @Override
    public CacheTransactionSession putInputStream(@NonNull String key, InputStream inputStream) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheTransaction) {
            return (CacheTransactionSession) ((ICacheTransaction) getTransactionListener()).putInputStream(key, inputStream);
        }
        return this;
    }

    @Override
    public CacheTransactionSession putObject(@NonNull String key, Object value) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheTransaction) {
            return (CacheTransactionSession) ((ICacheTransaction) getTransactionListener()).putObject(key, value);
        }
        return this;
    }

    @Override
    public String getString(@NonNull String key) {
        if (null != getTransactionListener()) {
            if (getTransactionListener() instanceof ICacheTransaction) {
                return ((ICacheTransaction) getTransactionListener()).getString(key);
            } else if (getTransactionListener() instanceof ICacheScheduledTransaction) {
                return ((ICacheScheduledTransaction) getTransactionListener()).getString(key);
            } else {
                // nothing to do
            }
        }
        return null;
    }

    @Override
    public InputStream getInputStream(@NonNull String key) {
        if (null != getTransactionListener()) {
            if (getTransactionListener() instanceof ICacheTransaction) {
                return ((ICacheTransaction) getTransactionListener()).getInputStream(key);
            } else if (getTransactionListener() instanceof ICacheScheduledTransaction) {
                return ((ICacheScheduledTransaction) getTransactionListener()).getInputStream(key);
            } else {
                // nothing to do
            }
        }
        return null;
    }

    @Override
    public Object getObject(@NonNull String key) {
        if (null != getTransactionListener()) {
            if (getTransactionListener() instanceof ICacheTransaction) {
                return ((ICacheTransaction) getTransactionListener()).getObject(key);
            } else if (getTransactionListener() instanceof ICacheScheduledTransaction) {
                return ((ICacheScheduledTransaction) getTransactionListener()).getObject(key);
            } else {
                // nothing to do
            }
        }
        return null;
    }

    @Override
    public CacheTransactionSession put(@NonNull String key, Object value) {
        if (null != getTransactionListener() && getTransactionListener() instanceof ICacheTransaction) {
            return (CacheTransactionSession) ((ICacheTransaction) getTransactionListener()).put(key, value);
        }
        return this;
    }

    @Override
    public Object get(@NonNull String key) {
        if (null != getTransactionListener()) {
            if (getTransactionListener() instanceof ICacheTransaction) {
                return ((ICacheTransaction) getTransactionListener()).get(key);
            } else if (getTransactionListener() instanceof ICacheScheduledTransaction) {
                return ((ICacheScheduledTransaction) getTransactionListener()).get(key);
            } else {
                // nothing to do
            }
        }
        return null;
    }

    @Override
    public CacheTransactionSession remove(@NonNull String key) {
        if (null != getTransactionListener()) {
            if (getTransactionListener() instanceof ICacheTransaction) {
                return (CacheTransactionSession) ((ICacheTransaction) getTransactionListener()).remove(key);
            } else if (getTransactionListener() instanceof ICacheScheduledTransaction) {
                return (CacheTransactionSession) ((ICacheScheduledTransaction) getTransactionListener()).remove(key);
            } else {
                // nothing to do
            }
        }
        return this;
    }

    @Override
    public boolean commit() {
        if (null != getTransactionListener()) {
            if (getTransactionListener() instanceof ICacheTransaction) {
                return ((ICacheTransaction) getTransactionListener()).commit();
            } else if (getTransactionListener() instanceof ICacheScheduledTransaction) {
                return ((ICacheScheduledTransaction) getTransactionListener()).commit();
            } else {
                // nothing to do
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CacheTransactionSession{" +
                "alias='" + alias + '\'' +
                '}';
    }
}
