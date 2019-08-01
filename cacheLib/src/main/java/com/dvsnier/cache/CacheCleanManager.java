package com.dvsnier.cache;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.base.IEvictableManager;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.CacheStorage;
import com.dvsnier.cache.infrastructure.Debug;
import com.dvsnier.cache.infrastructure.FileStorage;
import com.dvsnier.cache.wrapper.CacheWrapper;

import java.io.File;
import java.util.Enumeration;

import libcore.base.IBaseCache;

/**
 * the cache clean manager
 */
public class CacheCleanManager implements IEvictableManager {


    private static final CacheCleanManager INSTANCE = new CacheCleanManager();

    private CacheCleanManager() {
    }

    public static CacheCleanManager getInstance() {
        return INSTANCE;
    }

    //<editor-fold desc="Evictable,MultipleEvictable">

    @Hide
    @Override
    public boolean evict(@NonNull Type type) {
        IBaseCache baseCache = CacheManager.getInstance().getDefaultCache();
        if (null != baseCache) {
            if (baseCache instanceof CacheWrapper) {
                if (null != ((CacheWrapper) baseCache).getEngine()) {
                    return ((CacheWrapper) baseCache).getEngine().evict(type);
                }
            }
        }
        return false;
    }

    @Hide
    public boolean evict(String alias, @NonNull Type type) {
        Enumeration<IBaseCache> elements = CacheManager.getInstance().getCachePool().elements();
        while (elements.hasMoreElements()) {
            IBaseCache element = elements.nextElement();
            //noinspection ConditionCoveredByFurtherCondition
            if (null != element && element instanceof CacheWrapper) {
                if (((CacheWrapper) element).getAlias().equals(alias)) {
                    if (null != ((CacheWrapper) element).getEngine()) {
                        return ((CacheWrapper) element).getEngine().evict(type);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean evict(String type) {
        IBaseCache baseCache;
        if (TextUtils.isEmpty(type)) {
            baseCache = CacheManager.getInstance().getDefaultCache();
        } else {
            baseCache = CacheManager.getInstance().getCache(type);
        }
        if (null != baseCache) {
            if (baseCache instanceof CacheWrapper) {
                ((CacheWrapper) baseCache).evictAll();
                return true;
            }
        }
        return false;
    }

    @Override
    public void evictAll() {
        Enumeration<IBaseCache> elements = CacheManager.getInstance().getCachePool().elements();
        while (elements.hasMoreElements()) {
            IBaseCache element = elements.nextElement();
            //noinspection ConditionCoveredByFurtherCondition
            if (null != element && element instanceof CacheWrapper) {
                ((CacheWrapper) element).evictAll();
            }
        }

        File cacheDir = CacheStorage.INSTANCE().getCacheDir(CacheManager.getInstance().getContext());
        FileStorage.INSTANCE().deleteFiles(cacheDir);
        Debug.v(String.format("we have deleted all files and their sub folders in the cache engine directory(%s).", cacheDir.getAbsolutePath()));
    }

    //</editor-fold>

    /**
     * the delete the cache directory
     */
    public static void clearAllCache() {
        CacheCleanManager.INSTANCE.evictAll();
    }

    /**
     * the current cache size
     *
     * @param context {@link Context}
     * @return the total cache size
     */
    public static String getTotalCacheSize(Context context) {
        return getTotalCacheSize(context, false);
    }

    /**
     * the current cache size
     *
     * @param context {@link Context}
     * @param flag    true when is the sum of built-in disk storage and external disk storage, otherwise no
     * @return the total cache size
     */
    public static String getTotalCacheSize(Context context, boolean flag) {
        long cacheSize = 0;
        if (flag) cacheSize = FileStorage.INSTANCE().getFileSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += FileStorage.INSTANCE().getFileSize(context.getExternalCacheDir());
        } else {
            cacheSize = FileStorage.INSTANCE().getFileSize(context.getCacheDir());
        }
        return FileStorage.INSTANCE().getFormatted(cacheSize);
    }
}
