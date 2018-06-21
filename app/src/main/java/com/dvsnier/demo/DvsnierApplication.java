package com.dvsnier.demo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.dvsnier.cache.CacheManager;

/**
 * Created by lizw on 2017/6/23.
 */

public class DvsnierApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance().initialize(this);
//        int cacheMaxSizeOfDisk = 1024 * 1024 * 1024; // 1G
//        CacheManager.getInstance().initialize(new ICacheConfig.Builder(this).setAppVersion(getAppVersionCode(this)).setCacheMaxSizeOfDisk(cacheMaxSizeOfDisk).create());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        CacheManager.getInstance().close();
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
