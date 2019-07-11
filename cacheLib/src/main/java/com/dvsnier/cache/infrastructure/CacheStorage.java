package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.dvsnier.cache.BuildConfig;

import java.io.File;
import java.math.BigDecimal;

/**
 * CacheStorage
 * Created by dovsnier on 2018/6/12.
 */
public class CacheStorage implements ICacheStorage {

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
            Debug.e("the context object cannot be null");
            return null;
        }
        File cache;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()) {
            cache = context.getExternalCacheDir();
        } else {
            cache = context.getCacheDir();
        }
        Debug.i(String.format("the current root cache directory(%s) that is default settings ", cache.getAbsolutePath()));
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
            Debug.v(String.format("the current cache sdk(v%s) runs on external storage devices", BuildConfig.cache_sdk_version));
        } else {
            cache = context.getCacheDir();
            Debug.v(String.format("the current cache sdk(v%s) runs on internal storage devices", BuildConfig.cache_sdk_version));
        }
        //noinspection ConstantConditions
        if (null != uniqueName && !"".equals(uniqueName) && !"null".equals(uniqueName)) {
            if (null != cache) {
                File uniqueCache = new File(cache.getPath(), uniqueName);
                if (uniqueCache.exists()) {
                    Debug.i(String.format("the unique cache directory(%s) already exists", uniqueCache.getAbsolutePath()));
                } else {
                    //noinspection ResultOfMethodCallIgnored
                    cache.mkdirs();
                }
                return uniqueCache;
            }
        }
        return cache;
    }

    /**
     * the format unit with byte number
     *
     * @param bytes information number
     * @return the formatted information unit with specific quantities
     */
    public String getFormatted(double bytes) {
        double kiloByte = bytes / 1024;
        if (kiloByte < 1) {
            return bytes + "K";
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
        BigDecimal resultTera = new BigDecimal(Double.toString(teraBytes));
        return resultTera.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }

    /**
     * the format unit with byte number
     *
     * @param bytes information number
     * @return the formatted information unit with specific quantities
     * @deprecated
     */
    private double getFormattedNoUnit(double bytes) {
        double kiloByte = bytes / 1024;
        if (kiloByte < 1) {
            return bytes;
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal resultKilo = new BigDecimal(Double.toString(kiloByte));
            return resultKilo.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal resultMega = new BigDecimal(Double.toString(megaByte));
            return resultMega.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal resultGiga = new BigDecimal(Double.toString(gigaByte));
            return resultGiga.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        BigDecimal resultTera = new BigDecimal(Double.toString(teraBytes));
        return resultTera.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * the format unit with byte number
     *
     * @param bytes information number
     * @return the formatted information unit with specific quantities
     */
    public double getFormattedNoUnit(double bytes, @NonNull SCU unit) {
        if (bytes <= 0) return bytes;
        if (unit == SCU.B) {
            return bytes;
        }
        double kiloByte = bytes / 1024;
        if (kiloByte < 1) {
            return bytes;
        }
        if (unit == SCU.K) {
            return kiloByte;
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal resultKilo = new BigDecimal(Double.toString(kiloByte));
            return resultKilo.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if (unit == SCU.M) {
            return megaByte;
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal resultMega = new BigDecimal(Double.toString(megaByte));
            return resultMega.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if (unit == SCU.G) {
            return gigaByte;
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal resultGiga = new BigDecimal(Double.toString(gigaByte));
            return resultGiga.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        BigDecimal resultTera = new BigDecimal(Double.toString(teraBytes));
        return resultTera.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * scientific computing unit
     */
    public enum SCU {
        B, K, M, G, T
    }
}
