package com.dvsnier.cache.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by dovsnier on 2018/6/12.
 */
public class CacheUtil implements ICacheUtil {

    private static final CacheUtil INSTANCE = new CacheUtil();

    public static CacheUtil getInstance() {
        return INSTANCE;
    }

    private CacheUtil() {
    }

    @Override
    public File getDiskCacheDir(@NonNull Context context, @NonNull String uniqueName) {
        //noinspection ConstantConditions
        if (null == context) return null;
        File cache;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cache = context.getExternalCacheDir();
        } else {
            cache = context.getCacheDir();
        }
        //noinspection ConstantConditions
        if (null != uniqueName && !"".equals(uniqueName)) {
            if (null != cache) {
                File uniqueCache = new File(cache.getPath(), uniqueName);
                if (!uniqueCache.exists()) //noinspection ResultOfMethodCallIgnored
                    cache.mkdir();
                return uniqueCache;
            }
        }
        return cache;
    }
}
