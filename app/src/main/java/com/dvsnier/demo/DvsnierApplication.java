package com.dvsnier.demo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.dvsnier.cache.CacheManager;
import com.dvsnier.cache.ICacheConfig;

import java.io.File;

/**
 * Created by lizw on 2017/6/23.
 */

public class DvsnierApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializedCache();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        CacheManager.getInstance().close();
    }

    private void initializedCache() {
        File cache = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = getExternalCacheDir();
        } else {
            cache = getCacheDir();
        }
        int cacheMaxSizeOfDisk = 1024 * 1024 * 1024;
        CacheManager.getInstance().initialize(new ICacheConfig.Builder(this).setAppVersion(getAppVersionCode(this)).setCacheDirectory(cache).setCacheMaxSizeOfDisk(cacheMaxSizeOfDisk).create());
    }

    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
