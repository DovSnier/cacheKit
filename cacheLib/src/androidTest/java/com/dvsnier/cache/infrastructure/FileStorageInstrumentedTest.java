package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.dvsnier.cache.CacheManager;
import com.dvsnier.cache.annotation.PreCondition;
import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.CacheGenre;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.transaction.OnTransactionSessionChangeListener;
import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * FileStorageInstrumentedTest
 * Created by dovsnier on 2019-08-02.
 */
public class FileStorageInstrumentedTest {

    public Context context;
    protected static final String KEY_0 = "key_0";
    protected static final String KEY_1 = "key_1";
    protected static final String KEY_2 = "key_2";
    protected static final String KEY_3 = "key_3";
    protected static final String KEY_4 = "key_4";

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
        context = InstrumentationRegistry.getTargetContext();

        pre_conditions_0();
        pre_conditions_1();
    }

    @Test
    public void deleteFiles() {
        for (int i = 0; i < 10; i++) {
            put();
        }
        FileStorage.INSTANCE().deleteFiles(CacheStorage.INSTANCE().getCacheDir(context));
    }

    @Test
    public void deleteFiles_of_Directory() {
        FileStorage.INSTANCE().deleteFiles(CacheStorage.INSTANCE().getBaseDir(context));
    }

    @Test
    public void getFileSize() {
        for (int i = 0; i < 10; i++) {
            put();
        }
        long fileSize1 = FileStorage.INSTANCE().getFileSize(CacheStorage.INSTANCE().getCacheDir(context));
        Truth.assertThat(fileSize1).isGreaterThan(0);
        for (int i = 0; i < 10; i++) {
            put();
        }
        long fileSize2 = FileStorage.INSTANCE().getFileSize(CacheStorage.INSTANCE().getCacheDir(context));
        Truth.assertThat(fileSize2).isAtLeast(fileSize1);
    }

    @PreCondition
    protected void pre_conditions_0() {
        CacheManager.getInstance().initialize(context);

        CacheManager.getInstance().setOnTransactionSessionChangeListener(null, new OnTransactionSessionChangeListener() {
            @Override
            public void onTransactionSessionChange(@NonNull String alias, @NonNull String key, @Nullable Object value) {
                Log.i(Debug.TAG(), String.format("==> the current cache engine(%s), key(%s) - value(%s)", alias, key, value));
            }
        });
    }

    @Scheduled
    @PreCondition
    protected void pre_conditions_1() {
        CacheManager.getInstance().initialize(IType.TYPE_DOWNLOADS, new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(20, AbstractStorage.SCU.M)).intValue())
                .setUniqueName(IType.TYPE_DOWNLOADS)
                .setCacheGenre(new CacheGenre.SCHEDULED())
                .setDebug(true)
                .create());

        CacheManager.getInstance().setOnTransactionSessionChangeListener(IType.TYPE_DOWNLOADS, new OnTransactionSessionChangeListener() {
            @Override
            public void onTransactionSessionChange(@NonNull String alias, @NonNull String key, @Nullable Object value) {
                Log.i(Debug.TAG(), String.format("==> the current cache engine(%s), key(%s) - value(%s)", alias, key, value));
            }
        });

    }

    protected void put() {
        instance().put(KEY_1, System.currentTimeMillis())
                .put(KEY_2, System.currentTimeMillis(), 3, TimeUnit.SECONDS)
                .put(KEY_3, System.currentTimeMillis())
                .put(KEY_4, System.currentTimeMillis(), 1, TimeUnit.MINUTES)
                .put(String.valueOf(System.currentTimeMillis()), hashCode())
                .commit();
        instance().put(IType.TYPE_DOWNLOADS, KEY_1, System.currentTimeMillis())
                .put(IType.TYPE_DOWNLOADS, KEY_2, System.currentTimeMillis(), 3, TimeUnit.SECONDS)
                .put(IType.TYPE_DOWNLOADS, KEY_3, System.currentTimeMillis())
                .put(IType.TYPE_DOWNLOADS, KEY_4, System.currentTimeMillis(), 1, TimeUnit.MINUTES)
                .put(IType.TYPE_DOWNLOADS, String.valueOf(System.currentTimeMillis()), hashCode())
                .commit();
    }

    protected CacheManager instance() {
        return CacheManager.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        Debug.i("单元测试执行完成...");
    }
}