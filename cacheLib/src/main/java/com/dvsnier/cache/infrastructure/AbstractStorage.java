package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * AbstractStorage
 * Created by dovsnier on 2019-07-16.
 */
public abstract class AbstractStorage implements IStorage {

    @Override
    public File getBaseDir(@NonNull Context context) {
        //noinspection ConstantConditions
        if (null == context) {
            Debug.e("the context object cannot be null.");
            return null;
        }
        File baseDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()) {
            //noinspection ConstantConditions
            baseDir = context.getExternalCacheDir().getParentFile();
        } else {
            baseDir = context.getCacheDir().getParentFile();
        }
        Debug.i(String.format("the current root directory(%s) that is default system settings.", baseDir.getAbsolutePath()));
        return baseDir;
    }

    @Override
    public File getBaseDir(@NonNull Context context, @NonNull String uniqueName) {
        //noinspection ConstantConditions
        if (null == context) {
            Debug.e("the context object cannot be null.");
            return null;
        }
        if (TextUtils.isEmpty(uniqueName)) {
            Debug.e("the unique name cannot be empty.");
        }
        File baseDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()) {
            //noinspection ConstantConditions
            baseDir = context.getExternalCacheDir().getParentFile();
        } else {
            baseDir = context.getCacheDir().getParentFile();
        }
        if (null != baseDir) {
            File childFile = new File(baseDir, uniqueName);
            if (childFile.exists()) {
                Debug.i(String.format("the unique name directory(%s) already exists.", childFile.getAbsolutePath()));
            } else {
                //noinspection ResultOfMethodCallIgnored
                childFile.mkdirs();
                Debug.i(String.format("the current directory(%s) has been successfully created.", childFile.getAbsolutePath()));
            }
            return childFile;
        }
        //noinspection ConstantConditions
        Debug.i(String.format("the current root directory(%s) that is default system settings.", baseDir.getAbsolutePath()));
        return baseDir;
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
     * the format the actual number of specified numbers
     *
     * @param number the number
     * @param unit   {@link SCU}
     * @return the actual number of specified unit
     */
    public double getFormatted(int number, @NonNull SCU unit) {
        switch (unit) {
            case B:
                return number;
            case K:
                return number * 1024D;
            case M:
                return number * 1024D * 1024D;
            case G:
                return number * 1024D * 1024D * 1024D;
            case T:
                return number * 1024D * 1024D * 1024D * 1024D;
            default:
                return number;
        }
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
