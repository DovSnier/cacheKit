package com.dvsnier.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.dvsnier.cache.annotation.PreCondition;
import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.CacheGenre;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.AbstractStorage;
import com.dvsnier.cache.infrastructure.CacheStorage;
import com.dvsnier.cache.infrastructure.Debug;
import com.dvsnier.cache.transaction.OnTransactionSessionChangeListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * CacheCleanManagerInstrumentedTest
 * Created by dovsnier on 2019-08-01.
 */
public class CacheCleanManagerInstrumentedTest {

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

        put();
    }


    @Test
    public void evict() {
        put();
        get();
        cleanInstance().evict(IType.TYPE_NONE);
        cleanInstance().evict(IType.TYPE_DOWNLOADS);
    }

    @Test
    public void evict_of_type() {
        put();
        cleanInstance().evict(Type.DISK);
        cleanInstance().evict(IType.TYPE_DOWNLOADS, Type.MEMORY);
        put();
        get();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        get();
    }

    @Test
    public void evictAll() {
        put();
        get();
        CacheCleanManager.clearAllCache();
    }

    @Test
    public void getTotalCacheSize() {
        for (int i = 0; i < 10; i++) {
            put();
            get();
        }
        String size1 = CacheCleanManager.getTotalCacheSize(context);
        String size2 = CacheCleanManager.getTotalCacheSize(context, true);
        assertThat(size1).isNotEmpty();
        Debug.d(size1);
        assertThat(size2).isNotEmpty();
        Debug.d(size2);
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

    protected void get() {
        instance().get(KEY_1);
        instance().get(KEY_2);
        instance().get(KEY_3);
        instance().get(KEY_4);

        instance().get(IType.TYPE_DOWNLOADS, KEY_1);
        instance().get(IType.TYPE_DOWNLOADS, KEY_2);
        instance().get(IType.TYPE_DOWNLOADS, KEY_3);
        instance().get(IType.TYPE_DOWNLOADS, KEY_4);
    }

    protected CacheManager instance() {
        return CacheManager.getInstance();
    }

    protected CacheCleanManager cleanInstance() {
        return CacheCleanManager.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        CacheManager.getInstance().onDestroy();
        Debug.i("单元测试执行完成...");
    }
}