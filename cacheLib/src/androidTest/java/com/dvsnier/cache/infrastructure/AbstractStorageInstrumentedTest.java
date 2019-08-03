package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.google.common.truth.Truth.assertThat;

/**
 * AbstractStorageInstrumentedTest
 * Created by dovsnier on 2019-08-02.
 */
public class AbstractStorageInstrumentedTest {

    public Context context;

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getBaseDir() {
        File baseDir1 = FileStorage.INSTANCE().getBaseDir(context);
        assertThat(baseDir1).isNotNull();
        assertThat(baseDir1.isDirectory()).isTrue();
        assertThat(baseDir1.exists()).isTrue();
        File baseDir2 = FileStorage.INSTANCE().getBaseDir(context, AbstractStorageInstrumentedTest.class.getSimpleName());
        assertThat(baseDir2).isNotNull();
        assertThat(baseDir2.isDirectory()).isTrue();
        assertThat(baseDir2.exists()).isTrue();
    }

    @Test
    public void getExternalStorageDirectory() {
        File externalStorageDirectory = FileStorage.INSTANCE().getExternalStorageDirectory();
        assertThat(externalStorageDirectory).isNotNull();
        assertThat(externalStorageDirectory.exists()).isTrue();
        assertThat(externalStorageDirectory.isDirectory()).isTrue();
        debug(externalStorageDirectory.getAbsolutePath());
    }

    @Test
    public void getExternalStoragePublicDirectory() {
        File externalStorageDirectory0 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        assertThat(externalStorageDirectory0).isNotNull();
//        assertThat(externalStorageDirectory0.exists()).isTrue();
//        assertThat(externalStorageDirectory0.isDirectory()).isTrue();
        debug(externalStorageDirectory0.getAbsolutePath());

        File externalStorageDirectory1 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        assertThat(externalStorageDirectory1).isNotNull();
//        assertThat(externalStorageDirectory1.exists()).isTrue();
//        assertThat(externalStorageDirectory1.isDirectory()).isTrue();
        debug(externalStorageDirectory1.getAbsolutePath());

        File externalStorageDirectory2 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        assertThat(externalStorageDirectory2).isNotNull();
//        assertThat(externalStorageDirectory2.exists()).isTrue();
//        assertThat(externalStorageDirectory2.isDirectory()).isTrue();
        debug(externalStorageDirectory2.getAbsolutePath());

        File externalStorageDirectory3 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        assertThat(externalStorageDirectory3).isNotNull();
//        assertThat(externalStorageDirectory3.exists()).isTrue();
//        assertThat(externalStorageDirectory3.isDirectory()).isTrue();
        debug(externalStorageDirectory3.getAbsolutePath());

        File externalStorageDirectory4 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
        assertThat(externalStorageDirectory4).isNotNull();
//        assertThat(externalStorageDirectory4.exists()).isTrue();
//        assertThat(externalStorageDirectory4.isDirectory()).isTrue();
        debug(externalStorageDirectory4.getAbsolutePath());

        File externalStorageDirectory5 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        assertThat(externalStorageDirectory5).isNotNull();
//        assertThat(externalStorageDirectory5.exists()).isTrue();
//        assertThat(externalStorageDirectory5.isDirectory()).isTrue();
        debug(externalStorageDirectory5.getAbsolutePath());

        File externalStorageDirectory6 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
        assertThat(externalStorageDirectory6).isNotNull();
//        assertThat(externalStorageDirectory6.exists()).isTrue();
//        assertThat(externalStorageDirectory6.isDirectory()).isTrue();
        debug(externalStorageDirectory6.getAbsolutePath());

        File externalStorageDirectory7 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
        assertThat(externalStorageDirectory7).isNotNull();
//        assertThat(externalStorageDirectory7.exists()).isTrue();
//        assertThat(externalStorageDirectory7.isDirectory()).isTrue();
        debug(externalStorageDirectory7.getAbsolutePath());

        File externalStorageDirectory8 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        assertThat(externalStorageDirectory8).isNotNull();
//        assertThat(externalStorageDirectory8.exists()).isTrue();
//        assertThat(externalStorageDirectory8.isDirectory()).isTrue();
        debug(externalStorageDirectory8.getAbsolutePath());

        File externalStorageDirectory9 = FileStorage.INSTANCE().getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        assertThat(externalStorageDirectory9).isNotNull();
//        assertThat(externalStorageDirectory9.exists()).isTrue();
//        assertThat(externalStorageDirectory9.isDirectory()).isTrue();
        debug(externalStorageDirectory9.getAbsolutePath());

        String externalStorageDirectory10 = FileStorage.INSTANCE().getExternalStorageDirectory(context);
        assertThat(externalStorageDirectory10).isNotNull();
        assertThat(externalStorageDirectory10).isNotEmpty();
        debug(externalStorageDirectory10);

        File externalStorageDirectory11 = FileStorage.INSTANCE().setContext(context).getExternalStorageDirectory2();
        assertThat(externalStorageDirectory11).isNotNull();
        debug(externalStorageDirectory11.getAbsolutePath());
    }

    @Test
    public void getFormatted() {
        double bytes = 100;
        String formatted1 = FileStorage.INSTANCE().getFormatted(bytes);
        assertThat(formatted1).isNotEmpty();
        Debug.d(formatted1);
        String formatted2 = FileStorage.INSTANCE().getFormatted(bytes * 1000D);
        assertThat(formatted2).isNotEmpty();
        Debug.d(formatted2);
        String formatted3 = FileStorage.INSTANCE().getFormatted(bytes * 1000D * 1000D);
        assertThat(formatted3).isNotEmpty();
        Debug.d(formatted3);
        String formatted4 = FileStorage.INSTANCE().getFormatted(bytes * 1000D * 1000D * 1000D);
        assertThat(formatted4).isNotEmpty();
        Debug.d(formatted4);
        String formatted5 = FileStorage.INSTANCE().getFormatted(bytes * 1000D * 1000D * 1000D * 1000D);
        assertThat(formatted5).isNotEmpty();
        Debug.d(formatted5);
        String formatted6 = FileStorage.INSTANCE().getFormatted(bytes * 1000D * 1000D * 1000D * 1000D * 1000D);
        assertThat(formatted6).isNotEmpty();
        Debug.d(formatted6);
    }

    @Test
    public void getFormatted_of_scu() {
        int bytes = 100;
        double formatted1 = FileStorage.INSTANCE().getFormatted(bytes, AbstractStorage.SCU.B);
        assertThat(formatted1).isAtLeast(99);
        Debug.d(String.valueOf(formatted1));
        double formatted2 = FileStorage.INSTANCE().getFormatted(bytes, AbstractStorage.SCU.K);
        assertThat(formatted2).isAtLeast(99);
        Debug.d(String.valueOf(formatted2));
        double formatted3 = FileStorage.INSTANCE().getFormatted(bytes, AbstractStorage.SCU.M);
        assertThat(formatted3).isAtLeast(99);
        Debug.d(String.valueOf(formatted3));
        double formatted4 = FileStorage.INSTANCE().getFormatted(bytes, AbstractStorage.SCU.T);
        assertThat(formatted4).isAtLeast(99);
        Debug.d(String.valueOf(formatted4));
        double formatted5 = FileStorage.INSTANCE().getFormatted(bytes, AbstractStorage.SCU.G);
        assertThat(formatted5).isAtLeast(99);
        Debug.d(String.valueOf(formatted5));
    }

    @Deprecated
    @Test
    public void getFormattedNoUnit() {
//        double bytes = 100;
//        double formatted1 = FileStorage.INSTANCE().getFormattedNoUnit(bytes);
//        assertThat(formatted1).isAtLeast(0);
//        Debug.d(String.valueOf(formatted1));
//        double formatted2 = FileStorage.INSTANCE().getFormattedNoUnit(bytes * 1000D);
//        assertThat(formatted2).isAtLeast(0);
//        Debug.d(String.valueOf(formatted2));
//        double formatted3 = FileStorage.INSTANCE().getFormattedNoUnit(bytes * 1000D * 1000D);
//        assertThat(formatted3).isAtLeast(0);
//        Debug.d(String.valueOf(formatted3));
//        double formatted4 = FileStorage.INSTANCE().getFormattedNoUnit(bytes * 1000D * 1000D * 1000D);
//        assertThat(formatted4).isAtLeast(0);
//        Debug.d(String.valueOf(formatted4));
//        double formatted5 = FileStorage.INSTANCE().getFormattedNoUnit(bytes * 1000D * 1000D * 1000D * 1000D);
//        assertThat(formatted5).isAtLeast(0);
//        Debug.d(String.valueOf(formatted5));
//        double formatted6 = FileStorage.INSTANCE().getFormattedNoUnit(bytes * 1000D * 1000D * 1000D * 1000D * 1000D);
//        assertThat(formatted6).isAtLeast(0);
//        Debug.d(String.valueOf(formatted6));
    }

    @Test
    public void getFormattedNoUnit_of_scu() {
        double bytes = 1D; // B
        bytes *= 1024D; // K
        bytes *= 1024D; // M
//        bytes *= 1024D; // G
//        bytes *= 1024D; // T

        double formatted1 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.B);
        debug(AbstractStorage.SCU.B.toString() + ", " + formatted1);
        double formatted2 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.K);
        debug(AbstractStorage.SCU.K.toString() + ", " + formatted2);
        double formatted3 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.M);
        debug(AbstractStorage.SCU.M.toString() + ", " + formatted3);
        double formatted4 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.G);
        debug(AbstractStorage.SCU.G.toString() + ", " + formatted4);
        double formatted5 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.T);
        debug(AbstractStorage.SCU.T.toString() + ", " + formatted5);
    }

    @Test
    public void getFormattedNoUnit_of_scu_with_scale() {

        int scale = 4;

        double bytes = 1D; // B
        bytes *= 1024D; // K
        bytes *= 1024D; // M
        bytes *= 1024D; // G
//        bytes *= 1024D; // T

        double formatted1 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.B, scale);
        debug(AbstractStorage.SCU.B.toString() + ", " + formatted1);
        double formatted2 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.K, scale);
        debug(AbstractStorage.SCU.K.toString() + ", " + formatted2);
        double formatted3 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.M, scale);
        debug(AbstractStorage.SCU.M.toString() + ", " + formatted3);
        double formatted4 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.G, scale);
        debug(AbstractStorage.SCU.G.toString() + ", " + formatted4);
        double formatted5 = FileStorage.INSTANCE().getFormattedNoUnit(bytes, AbstractStorage.SCU.T, scale);
        debug(AbstractStorage.SCU.T.toString() + ", " + formatted5);
    }

    protected void debug(Object object) {
        if (null != object) Debug.d(String.format("%s", object));
    }

    @After
    public void tearDown() throws Exception {
        Debug.i("单元测试执行完成...");
    }
}