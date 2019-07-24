package com.dvsnier.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.annotation.PreCondition;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.infrastructure.AbstractStorage;
import com.dvsnier.cache.infrastructure.CacheStorage;
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

    public Context context;

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
        context = InstrumentationRegistry.getTargetContext();
        pre_conditions_1();
        pre_conditions_3();
    }

    @Test
    public void initialize() {
//        pre_conditions_1();
        pre_conditions_2();
        pre_conditions_3();
    }

    @PreCondition
    protected void pre_conditions_1() {
        CacheManager.getInstance().initialize(context);
    }

    @PreCondition
    protected void pre_conditions_2() {
        CacheManager.getInstance().initialize(new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(512, AbstractStorage.SCU.M)).intValue())
//                .setUniqueName(IType.TYPE_DEFAULT)
                .setUniqueName(IType.TYPE_DOWNLOADS)
                .setDebug(true)
//                .setLevel(Level.VERBOSE)
                .create());
    }

    @PreCondition
    protected void pre_conditions_3() {
        CacheManager.getInstance().initialize(IType.TYPE_HTTPS, new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(1, AbstractStorage.SCU.G)).intValue())
                .setUniqueName(IType.TYPE_HTTPS)
                .setDebug(true)
                .create());
    }

    protected CacheManager instance() {
        return CacheManager.getInstance();
    }

    @NonNull
    @Multiple
    protected String obtainMultipleType() {
        return IType.TYPE_HTTPS;
    }

    @Test
    public void getInstance() {
        Assert.assertNotNull(instance());
    }

    @Test
    public void put() {
        instance().put(KEY_1, System.currentTimeMillis());
    }

    @Test
    public void get() {
        put();
        Object o = instance().get(KEY_1);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(Long.class);
    }

    @Multiple
    @Test
    public void put_of_multiple() {
        instance().put(obtainMultipleType(), KEY_1, System.currentTimeMillis());
    }

    @Multiple
    @Test
    public void get_of_multiple() {
        put_of_multiple();
        Object o = instance().get(obtainMultipleType(), KEY_1);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(Long.class);
    }

    @Test
    public void putString() {
        instance().putString(KEY_2, String.valueOf(System.currentTimeMillis()));
    }

    @Test
    public void getString() {
        putString();
        Object o = instance().getString(KEY_2);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(String.class);
    }

    @Multiple
    @Test
    public void putString_of_multiple() {
        instance().putString(obtainMultipleType(), KEY_2, String.valueOf(System.currentTimeMillis()));
    }

    @Multiple
    @Test
    public void getString_of_multiple() {
        putString_of_multiple();
        Object o = instance().getString(obtainMultipleType(), KEY_2);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(String.class);
    }

    @Test
    public void putObject() {
        //noinspection UnnecessaryBoxing
        instance().putObject(KEY_3, Long.valueOf(System.currentTimeMillis()));
    }

    @Test
    public void getObject() {
        putObject();
        Object o = instance().getObject(KEY_3);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(Long.class);
    }

    @Multiple
    @Test
    public void putObject_of_multiple() {
        //noinspection UnnecessaryBoxing
        instance().putObject(obtainMultipleType(), KEY_3, Long.valueOf(System.currentTimeMillis()));
    }

    @Multiple
    @Test
    public void getObject_of_multiple() {
        putObject();
        Object o = instance().getObject(obtainMultipleType(), KEY_3);
        assertThat(o).isNotNull();
        assertThat(o).isInstanceOf(Long.class);
    }

    @Test
    public void putInputStream() {
        // nothing to do
    }

    @Test
    public void getInputStream() {
        // nothing to do
    }

    @Multiple
    @Test
    public void putInputStream_of_multiple() {
        // nothing to do
    }

    @Multiple
    @Test
    public void getInputStream_of_multiple() {
        // nothing to do
    }

    @Test
    public void remove() {
        instance().put(KEY_4, System.currentTimeMillis());
        Object o1 = instance().get(KEY_4);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        instance().remove(KEY_4);
        Object o2 = instance().get(KEY_4);
        assertThat(o2).isNull();
    }

    @Multiple
    @Test
    public void remove_of_multiple() {
        instance().put(obtainMultipleType(), KEY_4, System.currentTimeMillis());
        Object o1 = instance().get(obtainMultipleType(), KEY_4);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        instance().remove(obtainMultipleType(), KEY_4);
        Object o2 = instance().get(obtainMultipleType(), KEY_4);
        assertThat(o2).isNull();
    }

    @Test
    public void commit() {
        put();
        putString();
        putInputStream();
        putObject();
        boolean commit = instance().commit();
        assertThat(commit).isTrue();
    }

    @Multiple
    @Test
    public void commit_of_multiple() {
        put_of_multiple();
        putString_of_multiple();
        putInputStream_of_multiple();
        putObject_of_multiple();
        boolean commit = instance().commit(obtainMultipleType());
        assertThat(commit).isTrue();
    }

    @Test
    public void getDefaultCache() {
        assertThat(instance().getDefaultCache()).isNotNull();
    }

    @Test
    public void getCache() {
        assertThat(instance().getCache(obtainMultipleType())).isNotNull();
    }

    @Test
    public void close() {
        instance().close();
        assertThat(instance()).isNotNull();
        assertThat(instance().getDefaultCache()).isNull();
    }

    @Test
    public void onDestroy() {
        instance().onDestroy();
        assertThat(instance()).isNotNull();
        assertThat(instance().getDefaultCache()).isNull();
        assertThat(instance().getCachePool()).isEmpty();
    }

    @After
    public void tearDown() throws Exception {
        CacheManager.getInstance().onDestroy();
        Debug.i("单元测试执行完成...");
    }
}