package com.dvsnier.demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dvsnier.cache.CacheManager;
import com.dvsnier.cache.infrastructure.Debug;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static com.google.common.truth.Truth.assertThat;

/**
 * CacheManagerInstrumentedTest
 * Created by dovsnier on 2019-07-19.
 */
@FixMethodOrder(MethodSorters.JVM)
@RunWith(AndroidJUnit4.class)
public class CacheManagerInstrumentedTest {

    protected static final String TAG = CacheManagerInstrumentedTest.class.getSimpleName();
    protected static final String KEY_1 = "key_1";
    protected static final String KEY_2 = "key_2";
    protected static final String KEY_3 = "key_3";
    protected static final String KEY_4 = "key_4";

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
    }

    @Test
    public void initialize() {
        Context context = InstrumentationRegistry.getTargetContext();
        CacheManager.getInstance().initialize(context);
    }

    @Test
    public void getInstance() {
        Assert.assertNotNull(CacheManager.getInstance());
    }

    @Test
    public void put() {
        CacheManager.getInstance().put(KEY_1, System.currentTimeMillis());
    }

    @Test
    public void get() {
        Object o = CacheManager.getInstance().get(KEY_1);
        assertThat(o).isNotNull();
    }

    @Test
    public void putString() {
        CacheManager.getInstance().putString(KEY_2, String.valueOf(System.currentTimeMillis()));
    }

    @Test
    public void getString() {
        Object o = CacheManager.getInstance().getString(KEY_2);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(String.class);
    }

    @Test
    public void putObject() {
        //noinspection UnnecessaryBoxing
        CacheManager.getInstance().putObject(KEY_3, Long.valueOf(System.currentTimeMillis()));
    }

    @Test
    public void getObject() {
        Object o = CacheManager.getInstance().getObject(KEY_3);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(String.class);
    }

    @Test
    public void putInputStream() {
        // nothing to do
    }

    @Test
    public void getInputStream() {
        // nothing to do
    }

    @Test
    public void remove() {
        CacheManager.getInstance().put(KEY_4, System.currentTimeMillis());
        CacheManager.getInstance().remove(KEY_4);
        assertThat(CacheManager.getInstance().get(KEY_4)).isNull();
    }

    @Test
    public void commit() {
        CacheManager.getInstance().commit();
    }

    @Test
    public void getDefaultCache() {
        assertThat(CacheManager.getInstance().getDefaultCache()).isNotNull();
    }

    @Test
    public void close() {
        CacheManager.getInstance().close();
    }

    @Test
    public void onDestroy() {
        CacheManager.getInstance().onDestroy();
    }

    @After
    public void tearDown() throws Exception {
        Debug.i("单元测试执行完成.");
        CacheManager.getInstance().onDestroy();
    }

}