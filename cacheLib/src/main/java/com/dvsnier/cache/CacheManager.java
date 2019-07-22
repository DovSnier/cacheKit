package com.dvsnier.cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.base.AbstractCacheManager;
import com.dvsnier.cache.base.Closable;
import com.dvsnier.cache.base.IGetInstantiate;
import com.dvsnier.cache.config.CacheAllocation;
import com.dvsnier.cache.config.IAlias;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.config.OnCacheAllocationListener;
import com.dvsnier.cache.infrastructure.Debug;
import com.dvsnier.cache.transaction.CacheTransactionSession;
import com.dvsnier.cache.transaction.IGetCacheTransactionSession;
import com.dvsnier.cache.transaction.OnCacheTransactionListener;
import com.dvsnier.cache.transaction.OnTransactionSessionChangeListener;
import com.dvsnier.cache.wrapper.CacheWrapper;
import com.dvsnier.cache.wrapper.ICacheWrapper;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import libcore.base.IBaseCache;

/**
 * CacheManager
 * Created by dovsnier on 2016/9/26.
 */
public class CacheManager extends AbstractCacheManager {

    protected static String TAG = "CacheManager";
    @SuppressLint("StaticFieldLeak")
    private static CacheManager INSTANCE = new CacheManager();
    protected Context context;
    //    @Multiple
//    protected static final ConcurrentHashMap<String, IBaseCache> CACHE_POOL = new ConcurrentHashMap<>();
    @Multiple
    protected static ConcurrentHashMap<String, IBaseCache> CACHE_POOL;
    /* the default cache instantiate */
    protected static CacheWrapper cacheWrapper;
    /* the default cache allocation listener */
    protected OnCacheAllocationListener onCacheAllocationListener;


    private CacheManager() {
        CACHE_POOL = new ConcurrentHashMap<>();
        cacheWrapper = new CacheWrapper();
        if (!CACHE_POOL.containsKey(IType.TYPE_DEFAULT)) {
            CACHE_POOL.put(IType.TYPE_DEFAULT, cacheWrapper);
        }
    }

    public static CacheManager getInstance() {
        return INSTANCE;
    }

    //<editor-fold desc="ICacheEngine,IMultipleInstantiate">

    @Override
    public final void initialize(@NonNull Context context) {
        setContext(context);
        if (null != getCacheWrapper()) {
            getCacheWrapper().initialize(context);
            //noinspection ConstantConditions
            if (getCacheWrapper() instanceof IGetInstantiate) {
                if (getCacheWrapper().getInstantiate() instanceof IAlias) {
                    Debug.i(String.format("the default cache engine(%s) initialized.", ((IAlias) getCacheWrapper().getInstantiate()).getAlias()));
                }
            }
        }
    }

    @Multiple
    @Override
    public void initialize(@NonNull String type, @NonNull Context context) {
        //noinspection ConstantConditions
        if (null == context) {
            Debug.e(String.format("the context object instance(of %s) cannot be null.", type));
            //noinspection ThrowableNotThrown
            new IllegalAccessException("the context object instance cannot be null.");
        }
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof ICacheWrapper) {
                    ((ICacheWrapper) cache).initialize(context);
                    Debug.d(String.format("the %s cache engine initialized.", type));
                }
            } else {
                Debug.w(String.format("no %s cache engine was found from the cache pool and then rebuilt to build a %s cache engine.", type, type));
                ICacheWrapper cache = new CacheWrapper(type);
                getCachePool().put(type.trim(), cache);
                cache.initialize(context);
                Debug.d(String.format("the reconstruct build %s cache engine initialized.", type));
            }
        }
    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        //noinspection ConstantConditions
        if (null != cacheConfig) {
            setContext(cacheConfig.getContext());
            if (null != getCacheWrapper()) {
                getCacheWrapper().initialize(cacheConfig);
                //noinspection ConstantConditions
                if (getCacheWrapper() instanceof IGetInstantiate) {
                    if (getCacheWrapper().getInstantiate() instanceof IAlias) {
                        Debug.i(String.format("the default cache engine(%s) initialized.", ((IAlias) getCacheWrapper().getInstantiate()).getAlias()));
                    }
                }
            }
        }
    }

    @Multiple
    @Override
    public void initialize(@NonNull String type, @NonNull ICacheConfig cacheConfig) {
        //noinspection ConstantConditions
        if (null == cacheConfig) {
            Debug.e(String.format("the cacheConfig object instance(of %s) cannot be null.", type));
            //noinspection ThrowableNotThrown
            new IllegalAccessException("the cacheConfig object instance cannot be null.");
        }
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof ICacheWrapper) {
                    ((ICacheWrapper) cache).initialize(cacheConfig);
                    Debug.d(String.format("the %s cache engine initialized.", type));
                }
            } else {
                Debug.w(String.format("no %s cache engine was found from the cache pool and then rebuilt to build a %s cache engine.", type, type));
                ICacheWrapper cache = new CacheWrapper(type);
                getCachePool().put(type.trim(), cache);
                cache.initialize(cacheConfig);
                Debug.d(String.format("the reconstruct build %s cache engine initialized.", type));
            }
        }
    }

    @Override
    public final void close() {
        if (null != getCacheWrapper()) {
            getCacheWrapper().close();
            Debug.i(String.format("the default cache engine(%s) that has been shut down.", IType.TYPE_DEFAULT));
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
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().get(key);
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
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().getString(key);
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
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().getInputStream(key);
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
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().getObject(key);
                }
            }
        }
        return null;
    }

    @Override
    public CacheTransactionSession put(@NonNull String key, Object value) {
        return getCacheWrapper().getTransaction().put(key, value);
    }

    @Multiple
    @Override
    public CacheTransactionSession put(@NonNull String type, @NonNull String key, Object value) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().put(key, value);
                }
            }
        }
        return null;
    }

    @Override
    public CacheTransactionSession putString(@NonNull String key, String value) {
        return getCacheWrapper().getTransaction().putString(key, value);
    }

    @Multiple
    @Override
    public CacheTransactionSession putString(@NonNull String type, @NonNull String key, String value) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().putString(key, value);
                }
            }
        }
        return null;
    }

    @Override
    public CacheTransactionSession putInputStream(@NonNull String key, InputStream inputStream) {
        return getCacheWrapper().getTransaction().putInputStream(key, inputStream);
    }

    @Multiple
    @Override
    public CacheTransactionSession putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().putInputStream(key, inputStream);
                }
            }
        }
        return null;
    }

    @Override
    public CacheTransactionSession putObject(@NonNull String key, Object value) {
        return getCacheWrapper().getTransaction().putObject(key, value);
    }

    @Multiple
    @Override
    public CacheTransactionSession putObject(@NonNull String type, @NonNull String key, Object value) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().putObject(key, value);
                }
            }
        }
        return null;
    }

    @Override
    public CacheTransactionSession remove(@NonNull String key) {
        return getCacheWrapper().getTransaction().remove(key);
    }

    @Multiple
    @Override
    public CacheTransactionSession remove(@NonNull String type, @NonNull String key) {
        if (!TextUtils.isEmpty(type)) {
            if (getCachePool().containsKey(type.trim())) {
                IBaseCache cache = getCachePool().get(type);
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().remove(key);
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
                if (cache instanceof IGetCacheTransactionSession) {
                    //noinspection unchecked
                    return ((IGetCacheTransactionSession<CacheTransactionSession>) cache).getTransaction().commit();
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
                Debug.w(String.format("no %s cache engine was found from the cache pool and then rebuilt to build a %s cache engine", type, type));
                ICacheWrapper value = new CacheWrapper(type);
                getCachePool().put(type.trim(), value);
                value.initialize(context);
                Debug.d(String.format("the reconstruct build %s cache engine initialized", type));
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
                String alias = "";
                if (element instanceof IGetInstantiate) {
                    if (((IGetInstantiate) element).getInstantiate() instanceof IAlias) {
                        alias = ((IAlias) ((IGetInstantiate) element).getInstantiate()).getAlias();
                    }
                }
                ((Closable) element).close();
                Debug.d(String.format("the %s cache engine that has been shut down.", alias));
            }
        }
        getCachePool().clear();
        Debug.i("the cache engine pool has been clear.");
    }

    //</editor-fold>

    @Hide
    protected CacheWrapper getCacheWrapper() {
        return cacheWrapper;
    }

    @Hide
    protected void setCacheWrapper(CacheWrapper cacheWrapper) {
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

    /**
     * the setting up cached session listening
     *
     * @param type                       {@link IType}
     * @param onCacheTransactionListener {@link OnCacheTransactionListener}
     * @deprecated
     */
    @Hide
    public void setOnCacheTransactionListener(@NonNull String type, OnCacheTransactionListener onCacheTransactionListener) {
        if (TextUtils.isEmpty(type)) {
            if (getDefaultCache() instanceof CacheWrapper) {
                ((CacheWrapper) getDefaultCache()).setOnCacheTransactionListener(onCacheTransactionListener);
            }
        } else {
            IBaseCache cache = getCache(type);
            if (cache instanceof CacheWrapper) {
                ((CacheWrapper) cache).setOnCacheTransactionListener(onCacheTransactionListener);
            }
        }
    }

    /**
     * the setting up cached session listening
     *
     * @param type                               {@link IType}
     * @param onTransactionSessionChangeListener {@link OnTransactionSessionChangeListener}
     */
    public void setOnTransactionSessionChangeListener(@NonNull String type, OnTransactionSessionChangeListener onTransactionSessionChangeListener) {
        if (TextUtils.isEmpty(type)) {
            if (getDefaultCache() instanceof CacheWrapper) {
                ((CacheWrapper) getDefaultCache()).setOnTransactionSessionChangeListener(onTransactionSessionChangeListener);
            }
        } else {
            IBaseCache cache = getCache(type);
            if (cache instanceof CacheWrapper) {
                ((CacheWrapper) cache).setOnTransactionSessionChangeListener(onTransactionSessionChangeListener);
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
