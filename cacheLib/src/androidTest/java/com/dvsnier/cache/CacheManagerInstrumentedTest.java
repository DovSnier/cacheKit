package com.dvsnier.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.annotation.PreCondition;
import com.dvsnier.cache.annotation.Regulation;
import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.CacheGenre;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.IType;
import com.dvsnier.cache.infrastructure.AbstractStorage;
import com.dvsnier.cache.infrastructure.CacheStorage;
import com.dvsnier.cache.infrastructure.Debug;
import com.dvsnier.cache.infrastructure.FileStorage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import libcore.base.IBaseCache;

import static com.google.common.truth.Truth.assertThat;

/**
 * CacheManagerInstrumentedTest
 * Created by dovsnier on 2019-07-19.
 */
@FixMethodOrder(MethodSorters.JVM)
@RunWith(AndroidJUnit4.class)
public class CacheManagerInstrumentedTest {

    protected static final String TAG = CacheManagerInstrumentedTest.class.getSimpleName();
    protected static final String KEY_0 = "key_0";
    protected static final String KEY_1 = "key_1";
    protected static final String KEY_2 = "key_2";
    protected static final String KEY_3 = "key_3";
    protected static final String KEY_4 = "key_4";
    protected static final String FILE_TEMP = "temp.txt";

    public Context context;

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
        context = InstrumentationRegistry.getTargetContext();
        FileStorage.INSTANCE().setContext(context);
//        pre_conditions_0();
//        pre_conditions_1();
        pre_conditions_2();
        pre_conditions_3();
        pre_conditions_4();
        pre_conditions_5();
    }

    @Test
    public void initialize() {
        pre_conditions_0();
        pre_conditions_1();
        pre_conditions_2();
        pre_conditions_3();
        pre_conditions_4();
        pre_conditions_5();
        ConcurrentHashMap<String, IBaseCache> cachePool = CacheManager.getInstance().getCachePool();
        assertThat(cachePool).isNotEmpty();
    }

    @Test
    public void getInstance() {
        Assert.assertNotNull(instance());
    }

    @Test
    public void put() {
        instance().put(KEY_1, System.currentTimeMillis())
                .put(KEY_2, System.currentTimeMillis())
                .put(KEY_3, System.currentTimeMillis())
                .put(String.valueOf(System.currentTimeMillis()), hashCode())
                .commit();
    }

    @Test
    public void get() {
        put();
        Object o1 = instance().get(KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        Object o2 = instance().get(KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(Long.class);
        Object o3 = instance().get(KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
    }

    @Multiple
    @Test
    public void put_of_multiple() {
        instance().put(obtainDefaultType(), KEY_1, System.currentTimeMillis()).commit(obtainDefaultType());
        instance().put(obtainMultipleDefaultType(), KEY_1, System.currentTimeMillis()).commit(obtainMultipleDefaultType());
        instance().put(obtainScheduledType(), KEY_1, System.currentTimeMillis()).commit(obtainScheduledType());

        instance().put(String.valueOf(System.currentTimeMillis()), hashCode()).commit();
        instance().put(obtainDefaultType(), String.valueOf(System.currentTimeMillis()), hashCode()).commit(obtainDefaultType());
        instance().put(obtainMultipleDefaultType(), String.valueOf(System.currentTimeMillis()), hashCode()).commit(obtainMultipleDefaultType());
        instance().put(obtainScheduledType(), String.valueOf(System.currentTimeMillis()), hashCode()).commit(obtainScheduledType());
    }

    @Multiple
    @Test
    public void get_of_multiple() {
        put_of_multiple();
        Object o1 = instance().get(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        Object o2 = instance().get(obtainMultipleDefaultType(), KEY_1);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(Long.class);
        Object o3 = instance().get(obtainScheduledType(), KEY_1);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
    }

    @Scheduled
    @Test
    public void put_of_scheduled() {
        instance().put(KEY_0, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit();
        instance().put(obtainDefaultType(), KEY_1, System.currentTimeMillis()).commit(obtainDefaultType());
        instance().put(obtainDefaultType(), KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(obtainDefaultType());
        instance().put(obtainDefaultType(), KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(obtainDefaultType());

        instance().put(obtainMultipleDefaultType(), KEY_1, System.currentTimeMillis()).commit(obtainMultipleDefaultType());
        instance().put(obtainMultipleDefaultType(), KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(obtainMultipleDefaultType());
        instance().put(obtainMultipleDefaultType(), KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(obtainMultipleDefaultType());

        instance().put(obtainScheduledType(), KEY_1, System.currentTimeMillis()).commit(obtainScheduledType());
        instance().put(obtainScheduledType(), KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(obtainScheduledType());
        instance().put(obtainScheduledType(), KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(obtainScheduledType());

        instance().put(IType.TYPE_DOCUMENTS, KEY_1, System.currentTimeMillis()).commit(IType.TYPE_DOCUMENTS);
        instance().put(IType.TYPE_DOCUMENTS, KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(IType.TYPE_DOCUMENTS);
        instance().put(IType.TYPE_DOCUMENTS, KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(IType.TYPE_DOCUMENTS);
    }

    @Scheduled
    @Test
    public void get_of_scheduled() {
        put_of_scheduled();
//        CacheGenre#Scheduled
        Object o0 = instance().get(KEY_0);
        assertThat(o0).isNotNull();
        Object o1 = instance().get(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        Object o2 = instance().get(obtainDefaultType(), KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(Long.class);
        Object o3 = instance().get(obtainDefaultType(), KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o4 = instance().get(obtainDefaultType(), KEY_2);
        assertThat(o4).isNull(); // this is null , because of scheduled config
        Object o5 = instance().get(obtainDefaultType(), KEY_3);
        assertThat(o5).isNotNull();
//        CacheGenre#Default
        Object o6 = instance().get(obtainMultipleDefaultType(), KEY_1);
        assertThat(o6).isNotNull();
        assertThat(o6).isInstanceOf(Long.class);
        Object o7 = instance().get(obtainMultipleDefaultType(), KEY_2);
        assertThat(o7).isNotNull();
        assertThat(o7).isInstanceOf(Long.class);
        Object o8 = instance().get(obtainMultipleDefaultType(), KEY_3);
        assertThat(o8).isNotNull();
        assertThat(o8).isInstanceOf(Long.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o9 = instance().get(obtainMultipleDefaultType(), KEY_2);
        assertThat(o9).isNotNull(); // this is not null , because of default config
        Object o10 = instance().get(obtainMultipleDefaultType(), KEY_3);
        assertThat(o10).isNotNull();
//        CacheGenre#Scheduled
        Object o11 = instance().get(obtainScheduledType(), KEY_1);
        assertThat(o11).isNotNull();
        assertThat(o11).isInstanceOf(Long.class);
        Object o12 = instance().get(obtainScheduledType(), KEY_2);
        assertThat(o12).isNull(); // this is null , because of scheduled config
        Object o13 = instance().get(obtainScheduledType(), KEY_3);
        assertThat(o13).isNotNull();
        assertThat(o13).isInstanceOf(Long.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o14 = instance().get(obtainScheduledType(), KEY_2);
        assertThat(o14).isNull(); // this is null , because of scheduled config
        Object o15 = instance().get(obtainScheduledType(), KEY_3);
        assertThat(o15).isNotNull();
    }

    @Test
    public void putString() {
        instance().putString(KEY_1, String.valueOf(System.currentTimeMillis()))
                .putString(KEY_2, String.valueOf(System.currentTimeMillis()))
                .putString(KEY_3, String.valueOf(System.currentTimeMillis()))
                .putString(String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()))
                .commit();
    }

    @Test
    public void getString() {
        putString();
        Object o1 = instance().get(KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(String.class);
        Object o2 = instance().get(KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(String.class);
        Object o3 = instance().get(KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(String.class);
    }

    @Multiple
    @Test
    public void putString_of_multiple() {
        instance().putString(obtainDefaultType(), KEY_1, String.valueOf(System.currentTimeMillis())).commit(obtainDefaultType());
        instance().putString(obtainMultipleDefaultType(), KEY_1, String.valueOf(System.currentTimeMillis())).commit(obtainMultipleDefaultType());
        instance().putString(obtainScheduledType(), KEY_1, String.valueOf(System.currentTimeMillis())).commit(obtainScheduledType());

        instance().putString(String.valueOf(System.currentTimeMillis()), String.valueOf(hashCode())).commit();
        instance().putString(obtainDefaultType(), String.valueOf(System.currentTimeMillis()), String.valueOf(hashCode())).commit(obtainDefaultType());
        instance().putString(obtainMultipleDefaultType(), String.valueOf(System.currentTimeMillis()), String.valueOf(hashCode())).commit(obtainMultipleDefaultType());
        instance().putString(obtainScheduledType(), String.valueOf(System.currentTimeMillis()), String.valueOf(hashCode())).commit(obtainScheduledType());
    }

    @Multiple
    @Test
    public void getString_of_multiple() {
        putString_of_multiple();
        Object o1 = instance().getString(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(String.class);
        Object o2 = instance().getString(obtainMultipleDefaultType(), KEY_1);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(String.class);
        Object o3 = instance().getString(obtainScheduledType(), KEY_1);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(String.class);
    }

    @Scheduled
    @Test
    public void putString_of_scheduled() {
        instance().putString(KEY_0, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.MINUTES).commit();
        instance().putString(obtainDefaultType(), KEY_1, String.valueOf(System.currentTimeMillis())).commit(obtainDefaultType());
        instance().putString(obtainDefaultType(), KEY_2, String.valueOf(System.currentTimeMillis()), 200, TimeUnit.MILLISECONDS).commit(obtainDefaultType());
        instance().putString(obtainDefaultType(), KEY_3, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.MINUTES).commit(obtainDefaultType());

        instance().putString(obtainMultipleDefaultType(), KEY_1, String.valueOf(System.currentTimeMillis())).commit(obtainMultipleDefaultType());
        instance().putString(obtainMultipleDefaultType(), KEY_2, String.valueOf(System.currentTimeMillis()), 200, TimeUnit.MILLISECONDS).commit(obtainMultipleDefaultType());
        instance().putString(obtainMultipleDefaultType(), KEY_3, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.MINUTES).commit(obtainMultipleDefaultType());

        instance().putString(obtainScheduledType(), KEY_1, String.valueOf(System.currentTimeMillis())).commit(obtainScheduledType());
        instance().putString(obtainScheduledType(), KEY_2, String.valueOf(System.currentTimeMillis()), 200, TimeUnit.MILLISECONDS).commit(obtainScheduledType());
        instance().putString(obtainScheduledType(), KEY_3, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.MINUTES).commit(obtainScheduledType());

        instance().putString(IType.TYPE_DOCUMENTS, KEY_1, String.valueOf(System.currentTimeMillis())).commit(IType.TYPE_DOCUMENTS);
        instance().putString(IType.TYPE_DOCUMENTS, KEY_2, String.valueOf(System.currentTimeMillis()), 200, TimeUnit.MILLISECONDS).commit(IType.TYPE_DOCUMENTS);
        instance().putString(IType.TYPE_DOCUMENTS, KEY_3, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.MINUTES).commit(IType.TYPE_DOCUMENTS);
    }

    @Scheduled
    @Test
    public void getString_of_scheduled() {
        putString_of_scheduled();
//        CacheGenre#Scheduled
        Object o0 = instance().getString(KEY_0);
        assertThat(o0).isNotNull();
        Object o1 = instance().getString(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(String.class);
        Object o2 = instance().getString(obtainDefaultType(), KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(String.class);
        Object o3 = instance().getString(obtainDefaultType(), KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(String.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o4 = instance().getString(obtainDefaultType(), KEY_2);
        assertThat(o4).isNull(); // this is null , because of scheduled config
        Object o5 = instance().getString(obtainDefaultType(), KEY_3);
        assertThat(o5).isNotNull();
//        CacheGenre#Default
        Object o6 = instance().getString(obtainMultipleDefaultType(), KEY_1);
        assertThat(o6).isNotNull();
        assertThat(o6).isInstanceOf(String.class);
        Object o7 = instance().getString(obtainMultipleDefaultType(), KEY_2);
        assertThat(o7).isNotNull();
        assertThat(o7).isInstanceOf(String.class);
        Object o8 = instance().getString(obtainMultipleDefaultType(), KEY_3);
        assertThat(o8).isNotNull();
        assertThat(o8).isInstanceOf(String.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o9 = instance().getString(obtainMultipleDefaultType(), KEY_2);
        assertThat(o9).isNotNull(); // this is not null , because of default config
        Object o10 = instance().getString(obtainMultipleDefaultType(), KEY_3);
        assertThat(o10).isNotNull();
//        CacheGenre#Scheduled
        Object o11 = instance().getString(obtainScheduledType(), KEY_1);
        assertThat(o11).isNotNull();
        assertThat(o11).isInstanceOf(String.class);
        Object o12 = instance().getString(obtainScheduledType(), KEY_2);
        assertThat(o12).isNull(); // this is null , because of scheduled config
        Object o13 = instance().getString(obtainScheduledType(), KEY_3);
        assertThat(o13).isNotNull();
        assertThat(o13).isInstanceOf(String.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o14 = instance().getString(obtainScheduledType(), KEY_2);
        assertThat(o14).isNull(); // this is null , because of scheduled config
        Object o15 = instance().getString(obtainScheduledType(), KEY_3);
        assertThat(o15).isNotNull();
    }

    @Test
    public void putObject() {
        //noinspection UnnecessaryBoxing
        instance().putObject(KEY_1, Long.valueOf(System.currentTimeMillis()))
                .putObject(KEY_2, Long.valueOf(System.currentTimeMillis()))
                .putObject(KEY_3, Long.valueOf(System.currentTimeMillis()))
                .putObject(String.valueOf(System.currentTimeMillis()), hashCode())
                .commit();
    }

    @Test
    public void getObject() {
        putObject();
        Object o1 = instance().get(KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        Object o2 = instance().get(KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(Long.class);
        Object o3 = instance().get(KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
    }

    @Multiple
    @Test
    public void putObject_of_multiple() {
        //noinspection UnnecessaryBoxing
        instance().putObject(obtainDefaultType(), KEY_1, Long.valueOf(System.currentTimeMillis())).commit(obtainDefaultType());
        //noinspection UnnecessaryBoxing
        instance().putObject(obtainMultipleDefaultType(), KEY_1, Long.valueOf(System.currentTimeMillis())).commit(obtainMultipleDefaultType());
        //noinspection UnnecessaryBoxing
        instance().putObject(obtainScheduledType(), KEY_1, Long.valueOf(System.currentTimeMillis())).commit(obtainScheduledType());

        instance().putObject(String.valueOf(System.currentTimeMillis()), hashCode()).commit();
        instance().putObject(obtainDefaultType(), String.valueOf(System.currentTimeMillis()), hashCode()).commit(obtainDefaultType());
        instance().putObject(obtainMultipleDefaultType(), String.valueOf(System.currentTimeMillis()), hashCode()).commit(obtainMultipleDefaultType());
        instance().putObject(obtainScheduledType(), String.valueOf(System.currentTimeMillis()), hashCode()).commit(obtainScheduledType());
    }

    @Multiple
    @Test
    public void getObject_of_multiple() {
        putObject_of_multiple();
        Object o1 = instance().getObject(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        Object o2 = instance().getObject(obtainMultipleDefaultType(), KEY_1);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(Long.class);
        Object o3 = instance().getObject(obtainScheduledType(), KEY_1);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
    }

    @Scheduled
    @Test
    public void putObject_of_scheduled() {
        instance().putObject(KEY_0, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit();
        instance().putObject(obtainDefaultType(), KEY_1, System.currentTimeMillis()).commit(obtainDefaultType());
        instance().putObject(obtainDefaultType(), KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(obtainDefaultType());
        instance().putObject(obtainDefaultType(), KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(obtainDefaultType());

        instance().putObject(obtainMultipleDefaultType(), KEY_1, System.currentTimeMillis()).commit(obtainMultipleDefaultType());
        instance().putObject(obtainMultipleDefaultType(), KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(obtainMultipleDefaultType());
        instance().putObject(obtainMultipleDefaultType(), KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(obtainMultipleDefaultType());

        instance().putObject(obtainScheduledType(), KEY_1, System.currentTimeMillis()).commit(obtainScheduledType());
        instance().putObject(obtainScheduledType(), KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(obtainScheduledType());
        instance().putObject(obtainScheduledType(), KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(obtainScheduledType());

        instance().putObject(IType.TYPE_DOCUMENTS, KEY_1, System.currentTimeMillis()).commit(IType.TYPE_DOCUMENTS);
        instance().putObject(IType.TYPE_DOCUMENTS, KEY_2, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(IType.TYPE_DOCUMENTS);
        instance().putObject(IType.TYPE_DOCUMENTS, KEY_3, System.currentTimeMillis(), 1, TimeUnit.MINUTES).commit(IType.TYPE_DOCUMENTS);
    }

    @Scheduled
    @Test
    public void getObject_of_scheduled() {
        putObject_of_scheduled();
//        CacheGenre#Scheduled
        Object o0 = instance().getObject(KEY_0);
        assertThat(o0).isNotNull();
        Object o1 = instance().getObject(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        Object o2 = instance().getObject(obtainDefaultType(), KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(Long.class);
        Object o3 = instance().getObject(obtainDefaultType(), KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o4 = instance().getObject(obtainDefaultType(), KEY_2);
        assertThat(o4).isNull(); // this is null , because of scheduled config
        Object o5 = instance().getObject(obtainDefaultType(), KEY_3);
        assertThat(o5).isNotNull();
//        CacheGenre#Default
        Object o6 = instance().getObject(obtainMultipleDefaultType(), KEY_1);
        assertThat(o6).isNotNull();
        assertThat(o6).isInstanceOf(Long.class);
        Object o7 = instance().getObject(obtainMultipleDefaultType(), KEY_2);
        assertThat(o7).isNotNull();
        assertThat(o7).isInstanceOf(Long.class);
        Object o8 = instance().getObject(obtainMultipleDefaultType(), KEY_3);
        assertThat(o8).isNotNull();
        assertThat(o8).isInstanceOf(Long.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o9 = instance().getObject(obtainMultipleDefaultType(), KEY_2);
        assertThat(o9).isNotNull(); // this is not null , because of default config
        Object o10 = instance().getObject(obtainMultipleDefaultType(), KEY_3);
        assertThat(o10).isNotNull();
//        CacheGenre#Scheduled
        Object o11 = instance().getObject(obtainScheduledType(), KEY_1);
        assertThat(o11).isNotNull();
        assertThat(o11).isInstanceOf(Long.class);
        Object o12 = instance().getObject(obtainScheduledType(), KEY_2);
        assertThat(o12).isNull(); // this is null , because of scheduled config
        Object o13 = instance().getObject(obtainScheduledType(), KEY_3);
        assertThat(o13).isNotNull();
        assertThat(o13).isInstanceOf(Long.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o14 = instance().getObject(obtainScheduledType(), KEY_2);
        assertThat(o14).isNull(); // this is null , because of scheduled config
        Object o15 = instance().getObject(obtainScheduledType(), KEY_3);
        assertThat(o15).isNotNull();
    }

    @Test
    public void putInputStream() {
        InputStream inputStream1 = open(FILE_TEMP);
        InputStream inputStream2 = open(FILE_TEMP);
        InputStream inputStream3 = open(FILE_TEMP);
        InputStream inputStream4 = open(FILE_TEMP);
        instance().putInputStream(KEY_1, inputStream1)
                .putInputStream(KEY_2, inputStream2)
                .putInputStream(KEY_3, inputStream3)
                .putInputStream(String.valueOf(System.currentTimeMillis()), inputStream4)
                .commit();
    }

    @Test
    public void getInputStream() {
        putInputStream();
        Object o1 = instance().getInputStream(KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o1);
        Object o2 = instance().getInputStream(KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o2);
        Object o3 = instance().getInputStream(KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o3);
    }

    @Multiple
    @Test
    public void putInputStream_of_multiple() {
        InputStream inputStream1 = open(FILE_TEMP);
        InputStream inputStream2 = open(FILE_TEMP);
        InputStream inputStream3 = open(FILE_TEMP);
        InputStream inputStream4 = open(FILE_TEMP);
        InputStream inputStream5 = open(FILE_TEMP);
        InputStream inputStream6 = open(FILE_TEMP);
        InputStream inputStream7 = open(FILE_TEMP);
        instance().putInputStream(obtainDefaultType(), KEY_1, inputStream1).commit(obtainDefaultType());
        instance().putInputStream(obtainMultipleDefaultType(), KEY_1, inputStream2).commit(obtainMultipleDefaultType());
        instance().putInputStream(obtainScheduledType(), KEY_1, inputStream3).commit(obtainScheduledType());

        instance().putInputStream(String.valueOf(System.currentTimeMillis()), inputStream4).commit();
        instance().putInputStream(obtainDefaultType(), String.valueOf(System.currentTimeMillis()), inputStream5).commit(obtainDefaultType());
        instance().putInputStream(obtainMultipleDefaultType(), String.valueOf(System.currentTimeMillis()), inputStream6).commit(obtainMultipleDefaultType());
        instance().putInputStream(obtainScheduledType(), String.valueOf(System.currentTimeMillis()), inputStream7).commit(obtainScheduledType());
    }

    @Multiple
    @Test
    public void getInputStream_of_multiple() {
        put_of_multiple();

        Object o1 = instance().getInputStream(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o1);
        Object o2 = instance().getInputStream(obtainMultipleDefaultType(), KEY_1);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o2);
        Object o3 = instance().getInputStream(obtainScheduledType(), KEY_1);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o3);
    }

    @Scheduled
    @Test
    public void putInputStream_of_scheduled() {
        InputStream inputStream1 = open(FILE_TEMP);
        InputStream inputStream2 = open(FILE_TEMP);
        InputStream inputStream3 = open(FILE_TEMP);
        InputStream inputStream4 = open(FILE_TEMP);
        InputStream inputStream5 = open(FILE_TEMP);
        InputStream inputStream6 = open(FILE_TEMP);
        InputStream inputStream7 = open(FILE_TEMP);
        InputStream inputStream8 = open(FILE_TEMP);
        InputStream inputStream9 = open(FILE_TEMP);
        InputStream inputStream10 = open(FILE_TEMP);
        InputStream inputStream11 = open(FILE_TEMP);
        InputStream inputStream12 = open(FILE_TEMP);
        InputStream inputStream13 = open(FILE_TEMP);

        instance().putInputStream(KEY_0, inputStream1, 1, TimeUnit.MINUTES).commit();
        instance().putInputStream(obtainDefaultType(), KEY_1, inputStream2).commit(obtainDefaultType());
        instance().putInputStream(obtainDefaultType(), KEY_2, inputStream3, 200, TimeUnit.MILLISECONDS).commit(obtainDefaultType());
        instance().putInputStream(obtainDefaultType(), KEY_3, inputStream4, 1, TimeUnit.MINUTES).commit(obtainDefaultType());

        instance().putInputStream(obtainMultipleDefaultType(), KEY_1, inputStream5).commit(obtainMultipleDefaultType());
        instance().putInputStream(obtainMultipleDefaultType(), KEY_2, inputStream6, 200, TimeUnit.MILLISECONDS).commit(obtainMultipleDefaultType());
        instance().putInputStream(obtainMultipleDefaultType(), KEY_3, inputStream7, 1, TimeUnit.MINUTES).commit(obtainMultipleDefaultType());

        instance().putInputStream(obtainScheduledType(), KEY_1, inputStream8).commit(obtainScheduledType());
        instance().putInputStream(obtainScheduledType(), KEY_2, inputStream9, 200, TimeUnit.MILLISECONDS).commit(obtainScheduledType());
        instance().putInputStream(obtainScheduledType(), KEY_3, inputStream10, 1, TimeUnit.MINUTES).commit(obtainScheduledType());

        instance().putInputStream(IType.TYPE_DOCUMENTS, KEY_1, inputStream11).commit(IType.TYPE_DOCUMENTS);
        instance().putInputStream(IType.TYPE_DOCUMENTS, KEY_2, inputStream12, 200, TimeUnit.MILLISECONDS).commit(IType.TYPE_DOCUMENTS);
        instance().putInputStream(IType.TYPE_DOCUMENTS, KEY_3, inputStream13, 1, TimeUnit.MINUTES).commit(IType.TYPE_DOCUMENTS);
    }

    @Scheduled
    @Test
    public void getInputStream_of_scheduled() {
        putInputStream_of_scheduled();
//        CacheGenre#Scheduled
        Object o0 = instance().getInputStream(KEY_0);
        assertThat(o0).isNotNull();
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o0);
        Object o1 = instance().getInputStream(obtainDefaultType(), KEY_1);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o1);
        Object o2 = instance().getInputStream(obtainDefaultType(), KEY_2);
        assertThat(o2).isNotNull();
        assertThat(o2).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o2);
        Object o3 = instance().getInputStream(obtainDefaultType(), KEY_3);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o3);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o4 = instance().getInputStream(obtainDefaultType(), KEY_2);
        assertThat(o4).isNull(); // this is null , because of scheduled config
        //noinspection
//        FileStorage.INSTANCE().writeToLog((InputStream) o4);
        Object o5 = instance().getInputStream(obtainDefaultType(), KEY_3);
        assertThat(o5).isNotNull();
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o5);
//        CacheGenre#Default
        Object o6 = instance().getInputStream(obtainMultipleDefaultType(), KEY_1);
        assertThat(o6).isNotNull();
        assertThat(o6).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o6);
        Object o7 = instance().getInputStream(obtainMultipleDefaultType(), KEY_2);
        assertThat(o7).isNotNull();
        assertThat(o7).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o7);
        Object o8 = instance().getInputStream(obtainMultipleDefaultType(), KEY_3);
        assertThat(o8).isNotNull();
        assertThat(o8).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o8);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o9 = instance().getInputStream(obtainMultipleDefaultType(), KEY_2);
        assertThat(o9).isNotNull(); // this is not null , because of default config
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o9);
        Object o10 = instance().getInputStream(obtainMultipleDefaultType(), KEY_3);
        assertThat(o10).isNotNull();
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o10);
//        CacheGenre#Scheduled
        Object o11 = instance().getInputStream(obtainScheduledType(), KEY_1);
        assertThat(o11).isNotNull();
        assertThat(o11).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o11);
        Object o12 = instance().getInputStream(obtainScheduledType(), KEY_2);
        assertThat(o12).isNull(); // this is null , because of scheduled config
        //noinspection
//        FileStorage.INSTANCE().writeToLog((InputStream) o12);
        Object o13 = instance().getInputStream(obtainScheduledType(), KEY_3);
        assertThat(o13).isNotNull();
        assertThat(o13).isInstanceOf(InputStream.class);
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o13);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o14 = instance().getInputStream(obtainScheduledType(), KEY_2);
        assertThat(o14).isNull(); // this is null , because of scheduled config
        //noinspection
//        FileStorage.INSTANCE().writeToLog((InputStream) o14);
        Object o15 = instance().getInputStream(obtainScheduledType(), KEY_3);
        assertThat(o15).isNotNull();
        //noinspection CastCanBeRemovedNarrowingVariableType
        FileStorage.INSTANCE().writeToLog((InputStream) o15);
    }

    @Test
    public void remove() {
        instance().put(KEY_4, System.currentTimeMillis()).commit();
        Object o1 = instance().get(KEY_4);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        instance().remove(KEY_4);
        Object o2 = instance().get(KEY_4);
        assertThat(o2).isNull();

        instance().put(obtainDefaultType(), KEY_4, System.currentTimeMillis()).commit(obtainDefaultType());
        Object o3 = instance().get(KEY_4);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
        instance().remove(KEY_4);
        Object o4 = instance().get(KEY_4);
        assertThat(o4).isNull();
    }

    @Multiple
    @Test
    public void remove_of_multiple() {
        instance().put(obtainDefaultType(), KEY_4, System.currentTimeMillis()).commit(obtainDefaultType());
        Object o1 = instance().get(KEY_4);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        instance().remove(KEY_4);
        Object o2 = instance().get(KEY_4);
        assertThat(o2).isNull();

        instance().put(obtainMultipleDefaultType(), KEY_4, System.currentTimeMillis()).commit(obtainMultipleDefaultType());
        Object o3 = instance().get(obtainMultipleDefaultType(), KEY_4);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
        instance().remove(obtainMultipleDefaultType(), KEY_4);
        Object o4 = instance().get(obtainMultipleDefaultType(), KEY_4);
        assertThat(o4).isNull();

        instance().put(obtainScheduledType(), KEY_4, System.currentTimeMillis()).commit(obtainScheduledType());
        Object o5 = instance().get(obtainScheduledType(), KEY_4);
        assertThat(o5).isNotNull();
        assertThat(o5).isInstanceOf(Long.class);
        instance().remove(obtainScheduledType(), KEY_4);
        Object o6 = instance().get(obtainScheduledType(), KEY_4);
        assertThat(o6).isNull();
    }

    @Scheduled
    @Test
    public void remove_of_scheduled() {
        instance().put(obtainDefaultType(), KEY_4, System.currentTimeMillis(), 200, TimeUnit.MILLISECONDS).commit(obtainDefaultType());
        Object o1 = instance().get(KEY_4);
        assertThat(o1).isNotNull();
        assertThat(o1).isInstanceOf(Long.class);
        instance().remove(KEY_4);
        Object o2 = instance().get(KEY_4);
        assertThat(o2).isNull();

        instance().put(obtainMultipleDefaultType(), KEY_4, System.currentTimeMillis(), 400, TimeUnit.MILLISECONDS).commit(obtainMultipleDefaultType());
        Object o3 = instance().get(obtainMultipleDefaultType(), KEY_4);
        assertThat(o3).isNotNull();
        assertThat(o3).isInstanceOf(Long.class);
        instance().remove(obtainMultipleDefaultType(), KEY_4);
        Object o4 = instance().get(obtainMultipleDefaultType(), KEY_4);
        assertThat(o4).isNull();

        instance().put(obtainScheduledType(), KEY_4, System.currentTimeMillis(), 600, TimeUnit.MILLISECONDS).commit(obtainScheduledType());
        Object o5 = instance().get(obtainScheduledType(), KEY_4);
        assertThat(o5).isNotNull();
        assertThat(o5).isInstanceOf(Long.class);
        instance().remove(obtainScheduledType(), KEY_4);
        Object o6 = instance().get(obtainScheduledType(), KEY_4);
        assertThat(o6).isNull();
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
        boolean commit = instance().commit(obtainMultipleDefaultType());
        assertThat(commit).isTrue();
    }

    @Scheduled
    @Test
    public void commit_of_scheduled() {
        put_of_scheduled();
        putString_of_scheduled();
        putInputStream_of_scheduled();
        putObject_of_scheduled();
        boolean commit = instance().commit(obtainScheduledType());
        assertThat(commit).isTrue();
    }

    @Test
    public void getDefaultCache() {
        assertThat(instance().getDefaultCache()).isNotNull();
    }

    @Test
    public void getCache() {
        assertThat(instance().getCache(obtainMultipleDefaultType())).isNotNull();
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

    @Test
    public void deleteFiles() {
        FileStorage.INSTANCE().deleteFiles(CacheStorage.INSTANCE().getBaseDir(context));
    }

    @PreCondition
    protected void pre_conditions_0() {
        CacheManager.getInstance().initialize(context);
    }

    @PreCondition
    protected void pre_conditions_1() {
        CacheManager.getInstance().initialize(new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(20, AbstractStorage.SCU.M)).intValue())
                .setUniqueName(IType.TYPE_DOWNLOADS)
                .setDebug(true)
                .create());
    }

    @Scheduled
    @PreCondition
    protected void pre_conditions_2() {
        CacheManager.getInstance().initialize(new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(20, AbstractStorage.SCU.M)).intValue())
                .setUniqueName(IType.TYPE_DOWNLOADS)
                .setCacheGenre(new CacheGenre.SCHEDULED())
                .setDebug(true)
                .create());
    }

    @Multiple
    @PreCondition
    protected void pre_conditions_3() {
        CacheManager.getInstance().initialize(IType.TYPE_HTTPS, new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(30, AbstractStorage.SCU.M)).intValue())
                .setUniqueName(IType.TYPE_HTTPS)
                .setDebug(true)
                .create());
    }

    @Scheduled
    @Multiple
    @PreCondition
    protected void pre_conditions_4() {
        CacheManager.getInstance().initialize(IType.TYPE_MEDIA, new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(40, AbstractStorage.SCU.M)).intValue())
                .setUniqueName(IType.TYPE_MEDIA)
                .setCacheGenre(new CacheGenre.SCHEDULED())
                .setDebug(true)
                .create());
    }

    @Scheduled
    @Multiple
    @PreCondition
    protected void pre_conditions_5() {
        CacheManager.getInstance().initialize(IType.TYPE_DOCUMENTS, new ICacheConfig.Builder(context)
                .setContext(context)
                .setAppVersion(1)
                .setCacheMaxSizeOfDisk(Double.valueOf(CacheStorage.INSTANCE().getFormatted(50, AbstractStorage.SCU.M)).intValue())
                .setUniqueName(IType.TYPE_DOCUMENTS)
                .setCacheGenre(new CacheGenre.SCHEDULED())
                .setDebug(true)
                .create());
    }

    protected CacheManager instance() {
        return CacheManager.getInstance();
    }

    @PreCondition
    protected InputStream open(@NonNull String fileName) {
        try {
            return context.getAssets().open("mock" + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Regulation
    protected void debug(Object object) {
        if (null != object) Debug.d(String.format("%s", object));
    }

    @Regulation
    @NonNull
    protected String obtainDefaultType() {
        return IType.TYPE_DOWNLOADS;
    }

    @Regulation
    @NonNull
    @Multiple
    protected String obtainMultipleDefaultType() {
        return IType.TYPE_HTTPS;
    }

    @Regulation
    @NonNull
    @Multiple
    @Scheduled
    protected String obtainScheduledType() {
        return IType.TYPE_MEDIA;
    }

    @After
    public void tearDown() throws Exception {
        CacheManager.getInstance().onDestroy();
        Debug.i("单元测试执行完成...");
    }
}