package com.dvsnier.cache;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Hide;
import com.dvsnier.cache.base.Evictable;
import com.dvsnier.cache.base.IEvictableManager;
import com.dvsnier.cache.base.IGetInstantiate;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.CacheStorage;
import com.dvsnier.cache.infrastructure.Debug;

import java.io.File;
import java.math.BigDecimal;
import java.util.Enumeration;

import libcore.base.IBaseCache;

/**
 * the cache clean manager
 */
public class CacheCleanManager implements IEvictableManager {


    private static final CacheCleanManager INSTANCE = new CacheCleanManager();

    private CacheCleanManager() {
    }

    //<editor-fold desc="Evictable,MultipleEvictable">

    @Override
    public boolean evict(@NonNull Type type) {
        if (null != CacheManager.getInstance().getDefaultCache()) {
            if (CacheManager.getInstance().getDefaultCache() instanceof IGetInstantiate) {
                if (((IGetInstantiate) CacheManager.getInstance().getDefaultCache()).getInstantiate() instanceof Evictable) {
                    ((Evictable) ((IGetInstantiate) CacheManager.getInstance().getDefaultCache()).getInstantiate()).evict(type);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean evict(String type) {
        if (null != CacheManager.getInstance().getCache(type)) {
            if (CacheManager.getInstance().getCache(type) instanceof IGetInstantiate) {
                if (((IGetInstantiate) CacheManager.getInstance().getCache(type)).getInstantiate() instanceof Evictable) {
                    ((Evictable) ((IGetInstantiate) CacheManager.getInstance().getCache(type)).getInstantiate()).evictAll();
                    return true;
                }
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
            if (null != element && element instanceof IGetInstantiate) {
                if (((IGetInstantiate) element).getInstantiate() instanceof Evictable) {
                    ((Evictable) ((IGetInstantiate) element).getInstantiate()).evictAll();
                }
            }
        }

        File cacheDir = CacheStorage.INSTANCE().getCacheDir(CacheManager.getInstance().getContext());
        deleteDir(cacheDir);
        Debug.v(String.format("we have deleted all files and their sub folders in the cache engine directory(%s).", cacheDir.getAbsolutePath()));
    }

    //</editor-fold>

    /**
     * the current cache size
     *
     * @param context {@link Context}
     * @return the total cache size
     * @throws {@see Exception}
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * the delete the cache directory
     */
    public static void clearAllCache() {
        CacheCleanManager.INSTANCE.evictAll();
    }

    /**
     * to get folder size
     *
     * @param file the current file or directory
     * @return the file or directory size
     * @throws {@see Exception}
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    size = size + getFolderSize(listFiles[i]);
                } else {
                    size = size + listFiles[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * the format unit with folder size
     *
     * @param size folder size
     * @return the format unit with folder size
     */
    protected static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal resultKilo = new BigDecimal(Double.toString(kiloByte));
            return resultKilo.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal resultMega = new BigDecimal(Double.toString(megaByte));
            return resultMega.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal resultGiga = new BigDecimal(Double.toString(gigaByte));
            return resultGiga.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        BigDecimal resultTera = new BigDecimal(teraBytes);
        return resultTera.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }

    @Hide
    public static void deleteDir(File file) {
        if (null != file && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File itemFile : files) {
                    deleteDir(itemFile);
                }
            }
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }
}
