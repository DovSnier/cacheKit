package com.dvsnier.cache.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dvsnier.cache.BuildConfig;
import com.dvsnier.cache.R;
import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.annotation.Improvement;
import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.config.IAlias;
import com.dvsnier.cache.config.ICacheAPI;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.ICacheEngineConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.CacheStorage;
import com.dvsnier.cache.infrastructure.Debug;

import java.io.File;
import java.io.IOException;

import libcore.base.IBaseCache;
import libcore.base.IDiskLruCache;
import libcore.base.ILruCache;
import libcore.io.DiskLruCache;
import libcore.io.LruCache;

/**
 * Instantiate
 * Created by dovsnier on 2019-07-09.
 */
public class Instantiate implements ICacheEngine, ICacheEngineConfig, IAlias, Evictable, ICacheAPI {

    protected Context context;
    protected String alias;
    @Improvement(value = {"instantiate"})
    protected boolean instantiate;
    @Improvement(value = {"close"})
    protected boolean close;
    protected ILruCache lruCache;
    protected IDiskLruCache diskLruCache;

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
            long maxMemory = Runtime.getRuntime().maxMemory();
            int size = (int) (maxMemory / IBaseCache.DEFAULT_MAX_DENOMINATOR_OF_MEMORY);
            setContext(context);
            lruCache = new LruCache<>(size);
            File cache = CacheStorage.INSTANCE().getDiskCacheDir(context, context.getString(R.string.unique_name));
            Debug.d(String.format("the current cache engine(%s - memory:           %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(size, CacheStorage.SCU.M)));
            try {
                diskLruCache = DiskLruCache.open(cache, IBaseCache.ONE, IBaseCache.ONE, IBaseCache.DEFAULT_MAX_SIZE);
                Debug.d(String.format("the current cache engine(%s - disk directory:   %s)", getAlias(), cache.getAbsolutePath()));
                Debug.d(String.format("the current cache engine(%s - disk version:     %s)", getAlias(), IBaseCache.ONE));
                Debug.d(String.format("the current cache engine(%s - disk value count: %s)", getAlias(), IBaseCache.ONE));
                Debug.d(String.format("the current cache engine(%s - disk max size:    %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(IBaseCache.DEFAULT_MAX_SIZE, CacheStorage.SCU.M)));
            } catch (IOException e) {
                e.printStackTrace();
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache initialization", getAlias(), e.getMessage()));
            }
            onSdkCallback(context);
            setInstantiate(true);
        }
    }

//    @Override
//    public void initialize(@NonNull String type, @NonNull Context context) {
//        setAlias(type);
//        initialize(context);
//    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        if (isInstantiate()) {
            Debug.w(String.format("the current cache engine(%s) has been initialized. do not repeat the initialization operation.", getAlias()));
            return;
        } else {
            int cacheMaxSizeOfMemory = cacheConfig.getCacheMaxSizeOfMemory();
            if (cacheMaxSizeOfMemory <= IBaseCache.DEFAULT) {
                long maxMemory = Runtime.getRuntime().maxMemory();
                cacheMaxSizeOfMemory = (int) (maxMemory / IBaseCache.DEFAULT_MAX_DENOMINATOR_OF_MEMORY);
            }
            setContext(cacheConfig.getContext());
            lruCache = new LruCache<>(cacheMaxSizeOfMemory);
            File cacheDirectory = cacheConfig.getCacheDirectory();
            int appVersion = cacheConfig.getAppVersion();
            int valueCount = cacheConfig.getValueCount();
            long cacheMaxSizeOfDisk = cacheConfig.getCacheMaxSizeOfDisk();
            String uniqueName = cacheConfig.getUniqueName();
            if (!TextUtils.isEmpty(uniqueName) && !uniqueName.equals(getAlias())) {
                setAlias(uniqueName);
            }
            File directory = null != cacheDirectory ? cacheDirectory : TextUtils.isEmpty(uniqueName) ?
                    CacheStorage.INSTANCE().getDiskCacheDir(context, null) : CacheStorage.INSTANCE().getDiskCacheDir(context, uniqueName);
            Debug.d(String.format("the current cache engine(%s - memory:           %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(cacheMaxSizeOfMemory, CacheStorage.SCU.M)));
            int version = appVersion > IBaseCache.DEFAULT ? appVersion : IBaseCache.ONE;
            int count = valueCount > IBaseCache.DEFAULT ? valueCount : IBaseCache.ONE;
            long maxSize = cacheMaxSizeOfDisk > IBaseCache.DEFAULT ? cacheMaxSizeOfDisk : IBaseCache.DEFAULT_MAX_SIZE;
            try {
                diskLruCache = DiskLruCache.open(directory, version, count, maxSize);
                Debug.d(String.format("the current cache engine(%s - disk directory:   %s)", getAlias(), directory.getAbsolutePath()));
                Debug.d(String.format("the current cache engine(%s - disk version:     %s)", getAlias(), version));
                Debug.d(String.format("the current cache engine(%s - disk value count: %s)", getAlias(), count));
                Debug.d(String.format("the current cache engine(%s - disk max size:    %sm)", getAlias(), CacheStorage.INSTANCE().getFormattedNoUnit(maxSize, CacheStorage.SCU.M)));
            } catch (IOException e) {
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache initialization", getAlias(), e.getMessage()));
                e.printStackTrace();
            }
            onSdkCallback(cacheConfig.getContext());
            setInstantiate(true);
        }
    }

//    @Override
//    public void initialize(@NonNull String type, @NonNull ICacheConfig cacheConfig) {
//        setAlias(type);
//        initialize(cacheConfig);
//    }

    @Override
    public void close() {
        if (null != context) context = null;
        if (null != lruCache) lruCache = null;
        if (null != diskLruCache) {
            try {
                diskLruCache.close();
            } catch (IOException e) {
                Debug.e(String.format("the current cache engine(%s) is failure(%s) in disk cache closed", getAlias(), e.getMessage()));
                e.printStackTrace();
            }
            diskLruCache = null;
        }
        instantiate = false;
        close = true;
        Debug.i(String.format("the current cache engine(%s) is closed.", getAlias()));
    }

    @Override
    public boolean evict(@NonNull Type type) {
        switch (type) {
            case MEMORY:
                if (null != getLruCache()) {
                    getLruCache().evictAll();
                    Debug.d(String.format("the default cache engine(%s - %s) is evicted.", getAlias(), type.toString().toLowerCase()));
                }
                return true;
            case DISK:
                if (null != getDiskLruCache()) {
                    getDiskLruCache().evictAll();
                    Debug.d(String.format("the default cache engine(%s - %s) is evicted.", getAlias(), type.toString().toLowerCase()));
                }
                return true;
            case DEFAULT:
                evictAll();
                return true;
        }
        return false;
    }

    @Override
    public void evictAll() {
        if (null != getLruCache()) {
            getLruCache().evictAll();
        }
        if (null != getDiskLruCache()) {
            getDiskLruCache().evictAll();
        }
        Debug.i(String.format("the current cache engine(%s) is evicted.", getAlias()));
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

    @Multiple
    @Override
    public String getAlias() {
        return alias;
    }

    @Multiple
    @Override
    public void setAlias(String alias) {
        this.alias = alias;
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

    public ILruCache getLruCache() {
        return lruCache;
    }

    private void setLruCache(ILruCache lruCache) {
        this.lruCache = lruCache;
    }

    public IDiskLruCache getDiskLruCache() {
        return diskLruCache;
    }

    private void setDiskLruCache(IDiskLruCache diskLruCache) {
        this.diskLruCache = diskLruCache;
    }
}
