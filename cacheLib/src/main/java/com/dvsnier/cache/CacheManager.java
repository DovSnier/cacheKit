package com.dvsnier.cache;

import android.content.Context;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.util.LruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import libcore.io.DiskLruCache;

/**
 * Created by dovsnier on 2016/9/26.
 */
public class CacheManager implements ICache {

    public static String TAG = "CacheManager";
    private static CacheManager cacheManager;
    private static Context context;
    private static LruCache<String, Object> lruCache;
    private static DiskLruCache diskLruCache;
    private long maxSize = 512 * 1024 * 1024; // the default is 512m.

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (null == cacheManager) {
            synchronized (CacheManager.class) {
                if (null == cacheManager) {
                    cacheManager = new CacheManager();
                }
            }
        }
        return cacheManager;
    }

    @Override
    public final void initialize(Context context) {
        this.context = context;
        long maxMemory = Runtime.getRuntime().maxMemory();
        int size = (int) (maxMemory / 8);
        lruCache = new LruCache<>(size);
        File cache = getDiskCacheDir(context, null);
        try {
            diskLruCache = DiskLruCache.open(cache, 1, 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(ICacheConfig cacheConfig) {
        this.context = cacheConfig.getContext();
        int cacheMaxSizeOfMemory = cacheConfig.getCacheMaxSizeOfMemory();
        if (cacheMaxSizeOfMemory <= 0) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            cacheMaxSizeOfMemory = (int) (maxMemory / 8);
        }
        lruCache = new LruCache<>(cacheMaxSizeOfMemory);
        try {
            File cacheDirectory = cacheConfig.getCacheDirectory();
            int appVersion = cacheConfig.getAppVersion();
            int valueCount = cacheConfig.getValueCount();
            long cacheMaxSizeOfDisk = cacheConfig.getCacheMaxSizeOfDisk();
            diskLruCache = DiskLruCache.open(null != cacheDirectory ? cacheDirectory : getDiskCacheDir(context, null), appVersion > 0 ? appVersion : 1, valueCount > 0 ? valueCount : 1, cacheMaxSizeOfDisk > 0 ? cacheMaxSizeOfDisk : maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(String key) {
        if (null == lruCache) return null;
        if (null == key || "".equals(key)) return null;
        Object value = lruCache.get(key);
        if (null == value) {
            value = getObject(key);
        }
        return value;
    }

    @Override
    public String getString(String key) {
        if (null == diskLruCache) return null;
        if (null == key || "".equals(key)) return null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (null != snapshot) {
                return snapshot.getString(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream getInputStream(String key) {
        if (null == diskLruCache) return null;
        if (null == key || "".equals(key)) return null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (null != snapshot) {
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getObject(String key) {
        if (null == diskLruCache) return null;
        if (null == key || "".equals(key)) return null;
        Object value = null;
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (null != snapshot) {
                inputStream = snapshot.getInputStream(0);
                objectInputStream = new ObjectInputStream(inputStream);
                value = objectInputStream.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
                if (null != objectInputStream)
                    objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public ICache put(String key, Object value) {
        if (null == lruCache) return this;
        if (null == key || "".equals(key)) return this;
        if (null == value) return this;
        lruCache.put(key, value);
        if (value instanceof String) {
            putString(key, (String) value);
        } else if (value instanceof Parcelable) {
            putObject(key, value);
        } else if (value instanceof Serializable) {
            putObject(key, value);
        } else {
            // nothing to do
        }
        return this;
    }

    @Override
    public ICache putString(String key, String value) {
        if (null == diskLruCache) return this;
        if (null == value || "".equals(value)) return this;
        DiskLruCache.Editor edit = null;
        OutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            edit = diskLruCache.edit(key);
            outputStream = edit.newOutputStream(0);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
            bufferedWriter.write(value);
            bufferedWriter.flush();
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                edit.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (null != bufferedWriter)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public ICache putInputStream(String key, InputStream inputStream) {
        if (null == diskLruCache) return this;
        if (null == inputStream) return this;
        DiskLruCache.Editor edit = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        byte[] bytes = new byte[1024];
        try {
            edit = diskLruCache.edit(key);
            outputStream = edit.newOutputStream(0);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedInputStream = new BufferedInputStream(inputStream);
            int read = 0;
            while ((read = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes);
            }
            bufferedOutputStream.flush();
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                edit.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (null != bufferedInputStream)
                    bufferedInputStream.close();
                if (null != bufferedOutputStream)
                    bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public ICache putObject(String key, Object value) {
        if (null == diskLruCache) return this;
        if (null == value) return this;
        if ((value instanceof Parcelable) || value instanceof Serializable) {
            DiskLruCache.Editor edit = null;
            ObjectOutputStream objectOutputStream = null;
            OutputStream outputStream = null;
            try {
                edit = diskLruCache.edit(key);
                outputStream = edit.newOutputStream(0);
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(value);
                objectOutputStream.flush();
                edit.commit();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    edit.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    if (null != outputStream)
                        outputStream.close();
                    if (null != objectOutputStream)
                        objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    @Override
    public ICache remove(String key) {
        if (null == key || "".equals(key)) return this;
        if (null != lruCache)
            lruCache.remove(key);
        if (null != diskLruCache) {
            try {
                diskLruCache.remove(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public boolean commit() {
        try {
            if (null != getDiskCache())
                getDiskCache().flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @deprecated {@link ICache#commit()}
     */
    public final void onFlush() {
        commit();
    }

    @Override
    public LruCache getCache() {
        return lruCache;
    }

    @Override
    public DiskLruCache getDiskCache() {
        return diskLruCache;
    }

    @Override
    public final void close() {
        if (null != lruCache) lruCache = null;
        if (null != diskLruCache) {
            try {
                diskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            diskLruCache = null;
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        if (null == context) return null;
        File cache;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cache = context.getExternalCacheDir();
        } else {
            cache = context.getCacheDir();
        }
        if (null != uniqueName && !"".equals(uniqueName)) {
            File uniqueCache = new File(cache.getPath(), uniqueName);
            if (!uniqueCache.exists()) cache.mkdir();
            return uniqueCache;
        } else {
            return cache;
        }
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
            if (null != lruCache) lruCache.evictAll();
            if (null != diskLruCache) {
                try {
                    diskLruCache.delete();
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
