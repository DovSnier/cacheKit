package com.dvsnier.cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.dvsnier.cache.config.CacheAllocation;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.OnCacheAllocationListener;
import com.dvsnier.cache.transaction.ICacheTransaction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * Created by dovsnier on 2016/9/26.
 */
public class CacheManager implements ICache, ICacheTransaction {

    protected static String TAG = "CacheManager";
    private static CacheManager cacheManager;
    @SuppressLint("StaticFieldLeak")
    private static Cache cache;
    private OnCacheAllocationListener onCacheAllocationListener;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (null == cacheManager) {
            synchronized (CacheManager.class) {
                if (null == cacheManager) {
                    cacheManager = new CacheManager();
                    cache = new Cache();
                }
            }
        }
        return cacheManager;
    }

    //<editor-fold desc="ICache">

    @Override
    public final void initialize(@NonNull Context context) {
        if (null != cache) {
            cache.initialize(context);
        }
    }

    @Override
    public void initialize(@NonNull ICacheConfig cacheConfig) {
        if (null != cache) {
            //noinspection ConstantConditions
            if (null != cacheConfig) {
                cache.initialize(cacheConfig.getContext());
            }
        }
    }

    @Override
    public final void close() {
        if (null != cache) {
            cache.close();
        }
    }

    //</editor-fold>
    //<editor-fold desc="ICacheTransaction">

    @Override
    public Object get(@NonNull String key) {
        return cache.getCacheTransaction().get(key);
    }

    @Override
    public String getString(@NonNull String key) {
        return cache.getCacheTransaction().getString(key);
    }

    @Override
    public InputStream getInputStream(@NonNull String key) {
        return cache.getCacheTransaction().getInputStream(key);
    }

    @Override
    public Object getObject(@NonNull String key) {
        return cache.getCacheTransaction().getObject(key);
    }

    @Override
    public ICacheTransaction put(@NonNull String key, Object value) {
        return cache.getCacheTransaction().put(key, value);
    }

    @Override
    public ICacheTransaction putString(@NonNull String key, String value) {
        return cache.getCacheTransaction().putString(key, value);
    }

    @Override
    public ICacheTransaction putInputStream(@NonNull String key, InputStream inputStream) {
        return cache.getCacheTransaction().putInputStream(key, inputStream);
    }

    @Override
    public ICacheTransaction putObject(@NonNull String key, Object value) {
        return cache.getCacheTransaction().putObject(key, value);
    }

    @Override
    public ICacheTransaction remove(@NonNull String key) {
        return cache.getCacheTransaction().remove(key);
    }

    @Override
    public boolean commit() {
        return cache.getCacheTransaction().commit();
    }

    //</editor-fold>


    private OnCacheAllocationListener getOnCacheAllocationListener() {
        return onCacheAllocationListener;
    }

    public final void setOnCacheAllocationListener(OnCacheAllocationListener onCacheAllocationListener) {
        this.onCacheAllocationListener = onCacheAllocationListener;
        CacheAllocation.INSTANCE().setOnCacheAllocationListener(getOnCacheAllocationListener());
    }

    /**
     * the cache clean manager
     */
    public static class CacheCleanManager {

        /**
         * the current cache size
         *
         * @param context {@link Context}
         * @return the total cache size
         * @throws Exception
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
         *
         * @param context {@link Context}
         */
        public static void clearAllCache(Context context) {
            if (null != cache && null != cache.getCache()) cache.getCache().evictAll();
            if (null != cache && null != cache.getDiskCache()) {
                try {
                    cache.getDiskCache().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            deleteDir(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                deleteDir(context.getExternalCacheDir());
            }
        }

        /**
         * to get folder size
         *
         * @param file the current file or directory
         * @return the file or directory size
         * @throws Exception
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
        public static String getFormatSize(double size) {
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

        private static boolean deleteDir(File file) {
            if (file != null && file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(file, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            return file.delete();
        }
    }
}
