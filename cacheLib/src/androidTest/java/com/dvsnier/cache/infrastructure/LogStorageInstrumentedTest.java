package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * LogStorageInstrumentedTest
 * Created by dovsnier on 2019-08-02.
 */
public class LogStorageInstrumentedTest {

    protected Context context;

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void exec() {
//        final String[] cmdArray1 = {"logcat", "-b", "system"};
//        final String[] cmdArray1 = {"logcat", "-b", "radio"};
//        final String[] cmdArray1 = {"logcat", "-b", "events"};
        final String[] cmdArray1 = {"logcat", "-b", "main", "long"};
        LogStorage.INSTANCE().setContext(context)
                .submit(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        LogStorage.INSTANCE().exec(cmdArray1);
                        return null;
                    }
                });
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute() {
        LogStorage.INSTANCE().execute(context);
        try {
            Thread.sleep(10 * 1000);
//            Thread.sleep(25 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        Debug.i("单元测试执行完成...");
    }
}