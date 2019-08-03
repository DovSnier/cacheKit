package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.dvsnier.cache.annotation.PreCondition;
import com.dvsnier.cache.annotation.Regulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static com.google.common.truth.Truth.assertThat;

/**
 * AbstractFileStorageInstrumentedTest
 * Created by dovsnier on 2019-08-02.
 */
public class AbstractFileStorageInstrumentedTest {

    public Context context;
    protected final static String FILE_TEMP = "temp.txt";

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
        context = InstrumentationRegistry.getTargetContext();
        instance().setContext(context);
    }

    @Test
    public void closeQuietly() {
        InputStream inputStream = open(FILE_TEMP);
        if (null != inputStream) {
            instance().closeQuietly(inputStream);
        }
    }

    @Test
    public void read() {
        InputStream inputStream1 = open(FILE_TEMP);
        if (null != inputStream1) {
            CharSequence charSequence1 = instance().read(inputStream1);
            assertThat(charSequence1).isNotNull();
            debug(charSequence1);
        }

        InputStream inputStream2 = open(FILE_TEMP);
        if (null != inputStream2) {
            CharSequence charSequence2 = instance().read(new InputStreamReader(inputStream2));
            assertThat(charSequence2).isNotNull();
            debug(charSequence2);
        }
    }

    @Test
    public void readLine() {
        InputStream inputStream1 = open(FILE_TEMP);
        if (null != inputStream1) {
            CharSequence charSequence1 = instance().readLine(inputStream1);
            assertThat(charSequence1).isNotNull();
            debug(charSequence1);
        }

        InputStream inputStream2 = open(FILE_TEMP);
        if (null != inputStream2) {
            CharSequence charSequence2 = instance().readLine(new InputStreamReader(inputStream2));
            assertThat(charSequence2).isNotNull();
            debug(charSequence2);
        }
    }

    @Test
    public void nextLine() {
        InputStream inputStream1 = open(FILE_TEMP);
        if (null != inputStream1) {
            BufferedReader bufferedReader1 = instance().obtainReader(inputStream1);
            if (null != bufferedReader1) {
                while (true) {
                    CharSequence charSequence1 = instance().nextLine(bufferedReader1);
                    if (null == charSequence1) {
                        instance().closeQuietly(inputStream1);
                        break;
                    } else {
                        debug(charSequence1);
//                    assertThat(charSequence1).isNotNull();
                    }
                }
            }
        }

        InputStream inputStream2 = open(FILE_TEMP);
        if (null != inputStream2) {
            BufferedReader bufferedReader2 = instance().obtainReader(inputStream2);
            if (null != bufferedReader2) {
                while (true) {
                    CharSequence charSequence2 = instance().nextLine(bufferedReader2);
                    if (null == charSequence2) {
                        instance().closeQuietly(inputStream2);
                        break;
                    } else {
                        debug(charSequence2);
//                    assertThat(charSequence2).isNotNull();
                    }
                }
            }
        }
    }

    /**
     * writeToFile(InputStream)
     * writeToFile(Reader)
     * <p>
     * writeToLog(InputStream)
     * writeToLog(Reader)
     * <p>
     * writeToFile(InputStream,File) // log,temp,info
     * writeToFile(Reader,File) // log,temp,info
     */
    @Test
    public void writeToFile() throws InterruptedException, ExecutionException {
        writeToFile_01();
//        Thread.sleep(1000);
        writeToFile_23();
//        Thread.sleep(1000);
        writeToFile_45();

    }

    protected void writeToFile_45() throws ExecutionException, InterruptedException {
        Future<Void> future4 = instance().submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                InputStream inputStream4 = open(FILE_TEMP);
                instance().writeToFile(inputStream4, new File(instance().getBaseDir(context, IFileStorage.CONFIG), instance().getTempFileName()));
                InputStream inputStream5 = open(FILE_TEMP);
                instance().writeToFile(new InputStreamReader(inputStream5, IFileStorage.UTF_8), new File(instance().getBaseDir(context, IFileStorage.CONFIG), instance().getTempFileName()));
                return null;
            }
        });
        future4.get();
        Future<Void> future5 = instance().submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                InputStream inputStream6 = open(FILE_TEMP);
                instance().writeToFile(inputStream6, new File(instance().getBaseDir(context, IFileStorage.CONFIG), instance().getInfoFileName()));
                InputStream inputStream7 = open(FILE_TEMP);
                instance().writeToFile(new InputStreamReader(inputStream7, IFileStorage.UTF_8), new File(instance().getBaseDir(context, IFileStorage.CONFIG), instance().getInfoFileName()));
                return null;
            }
        });
        future5.get();
    }

    @PreCondition
    protected void writeToFile_23() throws ExecutionException, InterruptedException {
        debug("->5," + System.currentTimeMillis());
        final InputStream inputStream2 = open(FILE_TEMP);
        Future<Boolean> future1 = instance().submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                debug(String.format("    %s", System.currentTimeMillis()));
//                instance().writeToFile(new InputStreamReader(inputStream2, IFileStorage.UTF_8));
                debug(String.format("    %s", System.currentTimeMillis()));
                return true;
            }
        });
        debug("->6," + System.currentTimeMillis());
        if (future1.get()) {
            debug("->7," + System.currentTimeMillis());
            FutureTask<Boolean> task = new FutureTask<>(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    debug(String.format("    %s", System.currentTimeMillis()));
                    InputStream inputStream3 = open(FILE_TEMP);
                    instance().writeToFile(inputStream3, new File(instance().getBaseDir(context, IFileStorage.CONFIG), instance().getLogFileName()));
                    InputStream inputStream4 = open(FILE_TEMP);
                    instance().writeToFile(new InputStreamReader(inputStream4, IFileStorage.UTF_8), new File(instance().getBaseDir(context, IFileStorage.CONFIG), instance().getLogFileName()));
                    debug(String.format("    %s", System.currentTimeMillis()));
                    return true;
                }
            });
            instance().submit(task);
            debug("->8," + System.currentTimeMillis());
            task.get();
            debug("->9," + System.currentTimeMillis());
        }
        debug("->10," + System.currentTimeMillis());
    }

    @PreCondition
    protected void writeToFile_01() throws InterruptedException {
        final InputStream inputStream0 = open(FILE_TEMP);
        debug("->1," + System.currentTimeMillis());
        if (null != inputStream0) {
            instance().submit(new Runnable() {
                @Override
                public void run() {
                    debug(String.format("    %s", System.currentTimeMillis()));
                    instance().writeToFile(inputStream0);
                    instance().writeToFile(new InputStreamReader(inputStream0, IFileStorage.UTF_8));
                    debug(String.format("    %s", System.currentTimeMillis()));
                }
            });
        }
        debug("->2," + System.currentTimeMillis());
        Thread.sleep(1000);
        debug("->3," + System.currentTimeMillis());
        final InputStream inputStream1 = open(FILE_TEMP);
        if (null != inputStream1) {
            instance().submit(new Runnable() {
                @Override
                public void run() {
                    debug(String.format("    %s", System.currentTimeMillis()));
                    instance().writeToLog(inputStream1);
                    instance().writeToLog(new InputStreamReader(inputStream1, IFileStorage.UTF_8));
                    debug(String.format("    %s", System.currentTimeMillis()));
                }
            });
        }
        debug("->4," + System.currentTimeMillis());
    }

    @Test
    public void getNewFile() {
        File baseDir = instance().getBaseDir(context);
        String simpleName = AbstractFileStorageInstrumentedTest.class.getSimpleName();

        File newFile1 = instance().getNewFile(baseDir, simpleName + ".txt"); // file
        assertThat(newFile1).isNotNull();
        assertThat(newFile1.exists()).isTrue();
        assertThat(newFile1.isFile()).isTrue();
        assertThat(newFile1.canRead()).isTrue();
        assertThat(newFile1.canWrite()).isTrue();

        File newFile2 = instance().getNewFile(new File(baseDir, simpleName + ".tmp")); // file
        assertThat(newFile2).isNotNull();
        assertThat(newFile2.exists()).isTrue();
        assertThat(newFile2.isFile()).isTrue();
        assertThat(newFile2.canRead()).isTrue();
        assertThat(newFile2.canWrite()).isTrue();

        File newFile3 = instance().getNewFile(new File(baseDir, simpleName + ".log").getAbsolutePath()); // file
        assertThat(newFile3).isNotNull();
        assertThat(newFile3.exists()).isTrue();
        assertThat(newFile3.isFile()).isTrue();
        assertThat(newFile3.canRead()).isTrue();
        assertThat(newFile3.canWrite()).isTrue();
    }

    @Test
    public void getNewLogFile() {
        File newLogFile = instance().getNewLogFile();
        assertThat(newLogFile).isNotNull();
        assertThat(newLogFile.exists()).isTrue();
        assertThat(newLogFile.isFile()).isTrue();
    }

    @Test
    public void getNewInfoFile() {
        File newInfoFile = instance().getNewInfoFile();
        assertThat(newInfoFile).isNotNull();
        assertThat(newInfoFile.exists()).isTrue();
        assertThat(newInfoFile.isFile()).isTrue();
    }

    @Test
    public void getNewTempFile() {
        File newTempFile = instance().getNewTempFile();
        assertThat(newTempFile).isNotNull();
        assertThat(newTempFile.exists()).isTrue();
        assertThat(newTempFile.isFile()).isTrue();
    }

    @Test
    public void getFormatFileName() {
        String formatFileName0 = instance().getFormatFileName("yyyyMMddHHmm", "%s.txt");
        assertThat(formatFileName0).isNotEmpty();
        debug(formatFileName0);

        String formatFileName1 = instance().getLogFileName();
        assertThat(formatFileName1).isNotEmpty();
        debug(formatFileName1);

        String infoFileName1 = instance().getInfoFileName();
        assertThat(infoFileName1).isNotEmpty();
        debug(infoFileName1);

        String tempFileName1 = instance().getTempFileName();
        assertThat(tempFileName1).isNotEmpty();
        debug(tempFileName1);
    }

    protected FileStorage instance() {
        return FileStorage.INSTANCE();
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

    @After
    public void tearDown() throws Exception {
        Debug.i("单元测试执行完成...");
    }
}