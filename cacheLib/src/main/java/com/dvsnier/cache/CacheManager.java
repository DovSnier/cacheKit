package com.dvsnier.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.base.Closable;
import com.dvsnier.cache.base.ICacheEngine;
import com.dvsnier.cache.base.ICacheManager;
import com.dvsnier.cache.config.CacheAllocation;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.OnCacheAllocationListener;
import com.dvsnier.cache.transaction.ICacheTransaction;
import com.dvsnier.cache.transaction.IGetCacheTransaction;
import com.dvsnier.cache.transaction.ITransaction;
import com.dvsnier.cache.wrapper.CacheWrapper;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import libcore.base.IBaseCache;

/**
 * CacheManager
 * Created by dovsnier on 2016/9/26.
 */
public class CacheManager implements ICacheManager {

    protected static String TAG = "CacheManager";
    private static CacheManager INSTANCE = new CacheManager();
    @Multiple
    protected static final ConcurrentHashMap<String, IBaseCache> CACHE_POOL = new ConcurrentHashMap<>();
    /* the default cache instantiate */
    protected CacheWrapper cacheWrapper;
    /* the default cache allocation listener */
    protected OnCacheAllocationListener onCacheAllocationListener;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        return INSTANCE;
    }

    //<editor-fold desc="ICacheEngine,IMultipleInstantiate">

    @Override
    public final void initialize(@NonNull Context context) {
        if (null != getCacheWrapper()) {
            getCacheWrapper().initialize(context);
        }
    }

    @Multiple
    @Override
    public void initialize(@NonNull String type, @NonNull Context context) {
        //noinspection ConstantConditions
        if (null == context) {
            //noinspection ThrowableNotThrown
            new IllegalAccessException("the context object instance cannot be null");
        }
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof ICacheEngine) {
                    ((ICacheEngine) cache).initialize(context);
                }
            } else {
                CacheWrapper cache = new CacheWrapper();
                getCachePool().put(type.trim(), cache);
                //noinspection ConstantConditions
                if (cache instanceof ICacheEngine) {
                    ((ICacheEngine) cache).initialize(context);
                }
            }
        }
    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        if (null != getCacheWrapper()) {
            //noinspection ConstantConditions
            if (null != cacheConfig) {
                getCacheWrapper().initialize(cacheConfig);
            }
        }
    }

    @Multiple
    @Override
    public void initialize(@NonNull String type, @NonNull ICacheConfig cacheConfig) {
        //noinspection ConstantConditions
        if (null == cacheConfig) {
            //noinspection ThrowableNotThrown
            new IllegalAccessException("the cacheConfig object instance cannot be null");
        }
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof ICacheEngine) {
                    ((ICacheEngine) cache).initialize(cacheConfig);
                }
            } else {
                CacheWrapper cache = new CacheWrapper();
                getCachePool().put(type.trim(), cache);
                //noinspection ConstantConditions
                if (cache instanceof ICacheEngine) {
                    ((ICacheEngine) cache).initialize(cacheConfig);
                }
            }
        }
    }

    @Override
    public final void close() {
        if (null != getCacheWrapper()) {
            getCacheWrapper().close();
        }
        if (null != cacheWrapper) {
            cacheWrapper = null;
        }
        if (null != onCacheAllocationListener) {
            onCacheAllocationListener = null;
        }
    }

    //</editor-fold>
    //<editor-fold desc="ICacheTransaction,ICacheMultipleTransaction">

    @Override
    public Object get(@NonNull String key) {
        return getCacheWrapper().getTransaction().get(key);
    }

    @Multiple
    @Override
    public Object get(@NonNull String type, @NonNull String key) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().get(key);
                }
            }
        }
        return null;
    }

    @Override
    public String getString(@NonNull String key) {
        return getCacheWrapper().getTransaction().getString(key);
    }

    @Multiple
    @Override
    public String getString(@NonNull String type, @NonNull String key) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().getString(key);
                }
            }
        }
        return null;
    }

    @Override
    public InputStream getInputStream(@NonNull String key) {
        return getCacheWrapper().getTransaction().getInputStream(key);
    }

    @Multiple
    @Override
    public InputStream getInputStream(@NonNull String type, @NonNull String key) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().getInputStream(key);
                }
            }
        }
        return null;
    }

    @Override
    public Object getObject(@NonNull String key) {
        return getCacheWrapper().getTransaction().getObject(key);
    }

    @Multiple
    @Override
    public Object getObject(@NonNull String type, @NonNull String key) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().getObject(key);
                }
            }
        }
        return null;
    }

    @Override
    public ICacheTransaction put(@NonNull String key, Object value) {
        return (ICacheTransaction) getCacheWrapper().getTransaction().put(key, value);
    }

    @Multiple
    @Override
    public ITransaction put(@NonNull String type, @NonNull String key, Object value) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().put(key, value);
                }
            }
        }
        return null;
    }

    @Override
    public ICacheTransaction putString(@NonNull String key, String value) {
        return getCacheWrapper().getTransaction().putString(key, value);
    }

    @Multiple
    @Override
    public ICacheTransaction putString(@NonNull String type, @NonNull String key, String value) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().putString(key, value);
                }
            }
        }
        return null;
    }

    @Override
    public ICacheTransaction putInputStream(@NonNull String key, InputStream inputStream) {
        return getCacheWrapper().getTransaction().putInputStream(key, inputStream);
    }

    @Multiple
    @Override
    public ICacheTransaction putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().putInputStream(key, inputStream);
                }
            }
        }
        return null;
    }

    @Override
    public ICacheTransaction putObject(@NonNull String key, Object value) {
        return getCacheWrapper().getTransaction().putObject(key, value);
    }

    @Multiple
    @Override
    public ICacheTransaction putObject(@NonNull String type, @NonNull String key, Object value) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().putObject(key, value);
                }
            }
        }
        return null;
    }

    @Override
    public ICacheTransaction remove(@NonNull String key) {
        return (ICacheTransaction) getCacheWrapper().getTransaction().remove(key);
    }

    @Multiple
    @Override
    public ITransaction remove(@NonNull String type, @NonNull String key) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().remove(key);
                }
            }
        }
        return null;
    }

    @Override
    public boolean commit() {
        return getCacheWrapper().getTransaction().commit();
    }

    @Multiple
    @Override
    public boolean commit(@NonNull String type) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransaction) {
                    return ((IGetCacheTransaction) cache).getTransaction().commit();
                }
            }
        }
        return false;
    }

    //</editor-fold>
    //<editor-fold desc="ICachePool">


    @Override
    public IBaseCache getDefaultCache() {
        return getCacheWrapper();
    }

    @Override
    public IBaseCache getCache(String type) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                return getCachePool().get(type.trim());
            } else {
                CacheWrapper value = new CacheWrapper();
                getCachePool().put(type.trim(), value);
                return value;
            }
        }
        return null;
    }

    @Override
    public ConcurrentHashMap<String, IBaseCache> getCachePool() {
        return CACHE_POOL;
    }

    //</editor-fold>
    //<editor-fold desc="IDestroyer">

    @Override
    public void onDestroy() {
        Enumeration<IBaseCache> elements = getCachePool().elements();
        while (elements.hasMoreElements()) {
            IBaseCache element = elements.nextElement();
            //noinspection ConditionCoveredByFurtherCondition
            if (null != element && element instanceof Closable) {
                ((Closable) element).close();
            }
        }
    }

    //</editor-fold>

    public CacheWrapper getCacheWrapper() {
        return cacheWrapper;
    }

    public void setCacheWrapper(CacheWrapper cacheWrapper) {
        this.cacheWrapper = cacheWrapper;
    }

    private OnCacheAllocationListener getOnCacheAllocationListener() {
        return onCacheAllocationListener;
    }

    /**
     * the default cache allocation listener
     *
     * @param onCacheAllocationListener {@link OnCacheAllocationListener}
     */
    @Hide
    public final void setOnCacheAllocationListener(OnCacheAllocationListener onCacheAllocationListener) {
        this.onCacheAllocationListener = onCacheAllocationListener;
        CacheAllocation.INSTANCE().setOnCacheAllocationListener(getOnCacheAllocationListener());
    }
}
