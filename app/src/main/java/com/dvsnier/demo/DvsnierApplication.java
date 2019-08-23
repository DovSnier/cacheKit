package com.dvsnier.demo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dvsnier.cache.CacheManager;
import com.dvsnier.cache.base.CacheGenre;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.infrastructure.FileStorage;
import com.dvsnier.cache.infrastructure.LogStorage;
import com.dvsnier.cache.infrastructure.SimpleWriter;
import com.dvsnier.cache.infrastructure.WriteType;
import com.dvsnier.cache.transaction.OnTransactionSessionChangeListener;
import com.dvsnier.crash.Crash;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by lizw on 2017/6/23.
 */

public class DvsnierApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogStorage.INSTANCE().log(this);
        Crash.initialize(this);

        // 方式一
        CacheManager.getInstance().initialize(this);

        // 方式二
        int cacheMaxSizeOfDisk = 1024 * 1024 * 1024; // 1G
        CacheManager.getInstance().initialize(IType.TYPE_DOWNLOADS, new ICacheConfig.Builder(this)
                .setContext(this)
                .setAppVersion(getAppVersionCode(this))
                .setCacheMaxSizeOfDisk(cacheMaxSizeOfDisk)
//                .setUniqueName(IType.TYPE_DEFAULT)
                .setUniqueName(IType.TYPE_DOWNLOADS)
                .setCacheGenre(new CacheGenre.SCHEDULED())
                .setDebug(true)
//                .setLevel(Level.VERBOSE)
                .create());

        final File baseDir = FileStorage.INSTANCE().getBaseDir(DvsnierApplication.this);
        CacheManager.getInstance().setOnTransactionSessionChangeListener(null, new OnTransactionSessionChangeListener() {
            @Override
            public void onTransactionSessionChange(@NonNull final String alias, @NonNull final String key, @Nullable final Object value) {
                //                Log.i(Debug.TAG(), String.format("==> the current cache engine(%s), key(%s) - value(%s)", alias, key, value));
                FileStorage.INSTANCE().writeToFile(baseDir, "local.log", false, new SimpleWriter<Writer>(WriteType.WRITE) {
                    @Override
                    public boolean write(Writer writer) throws IOException {
                        writer.write(String.format("==> the current cache engine(%s), key(%s) - value(%s).\n", alias, key, value));
                        return true;
                    }
                });
            }
        });
        CacheManager.getInstance().setOnTransactionSessionChangeListener(IType.TYPE_DOWNLOADS, new OnTransactionSessionChangeListener() {
            @Override
            public void onTransactionSessionChange(@NonNull final String alias, @NonNull final String key, @Nullable final Object value) {
//                Log.i(Debug.TAG(), String.format("==> the current cache engine(%s), key(%s) - value(%s)", alias, key, value));
                FileStorage.INSTANCE().writeToFile(baseDir, String.format("%s.log", IType.TYPE_DOWNLOADS), false, new SimpleWriter<Writer>(WriteType.WRITE) {
                    @Override
                    public boolean write(Writer writer) throws IOException {
                        writer.write(String.format("==> the current cache engine(%s), key(%s) - value(%s).\n", alias, key, value));
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        CacheManager.getInstance().onDestroy();
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
