package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * AbstractStorage
 * Created by dovsnier on 2019-07-16.
 */
public abstract class AbstractStorage implements IStorage {

    protected Context context;

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
     * Return the primary shared/external storage directory. This directory may
     * not currently be accessible if it has been mounted by the user on their
     * computer, has been removed from the device, or some other problem has
     * happened.
     */
    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * Return the primary shared/external storage directory. This directory may
     * not currently be accessible if it has been mounted by the user on their
     * computer, has been removed from the device, or some other problem has
     * happened.
     */
    public String getExternalStorageDirectory(Context context) {
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(storageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return the primary shared/external storage directory. This directory may
     * not currently be accessible if it has been mounted by the user on their
     * computer, has been removed from the device, or some other problem has
     * happened.
     */
    public File getExternalStorageDirectory2() {
        if (null != getContext()) {
            return new File(getExternalStorageDirectory(getContext()));
        } else {
            return null;
        }
    }

    /**
     * Get a top-level shared/external storage directory for placing files of a
     * particular type. This is where the user will typically place and manage
     * their own files, so you should be careful about what you put here to
     * ensure you don't erase their files or get in the way of their own
     * organization.
     * <p>
     * On devices with multiple users (as described by {@see UserManager
     * }),
     * each user has their own isolated shared storage. Applications only have
     * access to the shared storage for the user they're running as.
     * </p>
     * <p>
     * Here is an example of typical code to manipulate a picture on the public
     */
    public File getExternalStoragePublicDirectory(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
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
            return bytes + "B";
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
        return getFormattedNoUnit(bytes, unit, 0);
    }

    /**
     * the format unit with byte number
     *
     * @param bytes information number
     * @param scale scale of the result returned
     * @return the formatted information unit with specific quantities
     */
    public double getFormattedNoUnit(double bytes, @NonNull SCU unit, int scale) {
        if (scale <= 0) {
            scale = 2;
        }
        if (bytes <= 0) return bytes;
        if (unit == SCU.B) {
            return bytes;
        }
        double kiloByte = bytes / 1024;
        if (unit == SCU.K) {
            if (kiloByte < 1) {
                BigDecimal resultKilo = new BigDecimal(Double.toString(kiloByte));
                return resultKilo.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            } else {
                return kiloByte;
            }
        }
        double megaByte = kiloByte / 1024;
        if (unit == SCU.M) {
            if (megaByte < 1) {
                BigDecimal resultMega = new BigDecimal(Double.toString(megaByte));
                return resultMega.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            } else {
                return megaByte;
            }
        }
        double gigaByte = megaByte / 1024;
        if (unit == SCU.G) {
            if (gigaByte < 1) {
                BigDecimal resultGiga = new BigDecimal(Double.toString(gigaByte));
                return resultGiga.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            } else {
                return gigaByte;
            }
        }
        double teraBytes = gigaByte / 1024;
        if (unit == SCU.T) {
            if (teraBytes < 1) {
                BigDecimal resultTera = new BigDecimal(Double.toString(teraBytes));
                return resultTera.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            } else {
                BigDecimal resultTera = new BigDecimal(Double.toString(teraBytes));
                return resultTera.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
        return bytes;
    }

    public Context getContext() {
        return context;
    }

    public AbstractStorage setContext(Context context) {
        this.context = context;
        return this;
    }

    /**
     * scientific computing unit
     */
    public enum SCU {
        B, K, M, G, T
    }
}
