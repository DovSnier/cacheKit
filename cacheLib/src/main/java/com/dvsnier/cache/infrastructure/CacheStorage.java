package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.dvsnier.cache.BuildConfig;

import java.io.File;

/**
 * CacheStorage
 * Created by dovsnier on 2018/6/12.
 */
public class CacheStorage extends AbstractStorage implements ICacheStorage {

    private static final CacheStorage INSTANCE = new CacheStorage();

    public static CacheStorage INSTANCE() {
        return INSTANCE;
    }

    private CacheStorage() {
    }

    @Override
    public boolean isExternalStorage() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()) {
            return true;
        }
        return false;
    }

    @Override
    public File getCacheDir(@NonNull Context context) {
        //noinspection ConstantConditions
        if (null == context) {
            Debug.e("the context object cannot be null.");
            return null;
        }
        File cache;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()) {
            cache = context.getExternalCacheDir();
        } else {
            cache = context.getCacheDir();
        }
        //noinspection ConstantConditions
        Debug.i(String.format("the current root cache directory(%s) that is default settings.", cache.getAbsolutePath()));
        return cache;
    }

    @Override
    public File getDiskCacheDir(@NonNull Context context, @NonNull String uniqueName) {
        //noinspection ConstantConditions
        if (null == context) {
            Debug.e("the context object cannot be null");
            return null;
        }
        File cache;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()) {
            cache = context.getExternalCacheDir();
            Debug.v(String.format("the current cache sdk(v%s) runs on external storage devices.", BuildConfig.cache_sdk_version));
        } else {
            cache = context.getCacheDir();
            Debug.v(String.format("the current cache sdk(v%s) runs on internal storage devices.", BuildConfig.cache_sdk_version));
        }
        //noinspection ConstantConditions
        if (null != uniqueName && !"".equals(uniqueName) && !"null".equals(uniqueName)) {
            if (null != cache) {
                File uniqueCache = new File(cache.getPath(), uniqueName);
                if (uniqueCache.exists()) {
                    Debug.i(String.format("the unique cache directory(%s) already exists.", uniqueCache.getAbsolutePath()));
                } else {
                    //noinspection ResultOfMethodCallIgnored
                    uniqueCache.mkdirs();
                }
                return uniqueCache;
            }
        }
        return cache;
    }
}
