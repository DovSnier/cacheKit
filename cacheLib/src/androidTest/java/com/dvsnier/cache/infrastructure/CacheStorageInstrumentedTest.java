package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.google.common.truth.Truth.assertThat;

/**
 * CacheStorageInstrumentedTest
 * Created by dovsnier on 2019-08-02.
 */
public class CacheStorageInstrumentedTest {

    public Context context;

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void isExternalStorage() {
        boolean externalStorage = CacheStorage.INSTANCE().isExternalStorage();
        assertThat(externalStorage).isTrue();
    }

    @Test
    public void getCacheDir() {
        File cacheDir = CacheStorage.INSTANCE().getCacheDir(context);
        assertThat(cacheDir).isNotNull();
        assertThat(cacheDir.exists()).isTrue();
    }

    @Test
    public void getDiskCacheDir() {
        File diskCacheDir1 = CacheStorage.INSTANCE().getDiskCacheDir(context, null);
        assertThat(diskCacheDir1).isNotNull();
        assertThat(diskCacheDir1.exists()).isTrue();
        File diskCacheDir2 = CacheStorage.INSTANCE().getDiskCacheDir(context, CacheStorageInstrumentedTest.class.getSimpleName());
        assertThat(diskCacheDir2).isNotNull();
        assertThat(diskCacheDir2.exists()).isTrue();
    }

    @After
    public void tearDown() throws Exception {
        Debug.i("单元测试执行完成...");
    }
}