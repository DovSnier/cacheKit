package com.dvsnier.cache.infrastructure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * AbstractFileStorage
 * Created by dovsnier on 2019-07-16.
 */
public abstract class AbstractFileStorage extends AbstractStorage implements IFileStorage {

    protected Context context;

    public void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (RuntimeException runtimeException) {
                throw runtimeException;
            } catch (Exception exception) {
                Debug.e(String.format("the current closed flow operation exception(%s).", exception.getMessage()));
                exception.printStackTrace();
            }
        }
    }

    @Override
    public CharSequence read(@NonNull InputStream inputStream) {
        //noinspection ConstantConditions
        if (null != inputStream) {
            return read(new InputStreamReader(inputStream, UTF_8));
        }
        return null;
    }

    @Override
    public CharSequence read(@NonNull Reader reader) {
        StringWriter writer = new StringWriter();
        try {
            char[] chars = new char[DEFAULT_SIZE];
            int count;
            while (EOF != (count = reader.read(chars))) {
                writer.write(chars, 0, count);
            }
        } catch (IOException e) {
            Debug.e(String.format("the io exception(%s) occurred when read data.", e.getMessage()));
            e.printStackTrace();
        } finally {
            closeQuietly(reader);
            closeQuietly(writer);
        }
        return writer.toString();
    }

    public CharSequence readLine(@NonNull InputStream inputStream) {
        //noinspection ConstantConditions
        if (null != inputStream) {
            return readLine(new InputStreamReader(inputStream, UTF_8));
        }
        return null;
    }

    public CharSequence readLine(@NonNull Reader reader) {
        StringBuilder stringBuilder = new StringBuilder(DEFAULT_SIZE);
        //noinspection ConstantConditions
        if (null != reader) {
            BufferedReader bufferedReader = new BufferedReader(reader, DEFAULT_SIZE);
            try {
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (null == readLine) {
                        break;
                    } else {
                        stringBuilder.append(readLine);
                    }
                }
            } catch (IOException e) {
                Debug.e(String.format("the io exception(%s) occurred when read data.", e.getMessage()));
                e.printStackTrace();
            } finally {
                closeQuietly(reader);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToFile(@NonNull InputStream inputStream) {
        writeToFile(inputStream, null, NONE, FLAG_SINGLE_MODE);
    }

    @Override
    public void writeToFile(@NonNull InputStream inputStream, @NonNull File uniqueName) {
        writeToFile(inputStream, uniqueName, NONE, FLAG_NEW_FILE_NAME);
    }

    @Override
    public void writeToFile(@NonNull InputStream inputStream, int size) {
        writeToFile(inputStream, null, size, FLAG_MULTI_MODE);
    }

    protected final void writeToFile(@NonNull InputStream inputStream, @Nullable File uniqueName, int size, int flag) {
        //noinspection ConstantConditions
        if (null == inputStream) {
            Debug.e(String.format("the input stream parameters(%s) cannot be null.", inputStream));
            throw new IllegalArgumentException("the input stream parameters cannot be null.");
        }
        if (flag == FLAG_MULTI_MODE) {
            if (size <= 0) {
                Debug.e(String.format("the size parameters(%s) cannot be less than or equal to zero.", size));
                throw new IllegalArgumentException("the size parameters cannot be less than or equal to zero.");
            }
        }
        byte[] bytes = new byte[DEFAULT_SIZE];
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        File file;
        if (flag == FLAG_NEW_FILE_NAME) {
            //noinspection ConstantConditions
            file = getNewFile(uniqueName);
        } else {
            file = getNewLogFile();
        }
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, true), DEFAULT_SIZE);
            bufferedInputStream = new BufferedInputStream(inputStream, DEFAULT_SIZE);
            int count;
            while (EOF != (count = bufferedInputStream.read(bytes))) {
                bufferedOutputStream.write(bytes, 0, count);
                bufferedOutputStream.flush();
                if (flag == FLAG_MULTI_MODE) {
                    if (file.length() > size) {
                        file = getNewLogFile();
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, true), DEFAULT_SIZE);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Debug.e(String.format("the file not found exception(%s) occurred when writing data to file.", e.getMessage()));
            e.printStackTrace();
        } catch (IOException e) {
            Debug.e(String.format("the io exception(%s) occurred when writing data to file.", e.getMessage()));
            e.printStackTrace();
        } finally {
            closeQuietly(inputStream);
            closeQuietly(bufferedInputStream);
            closeQuietly(bufferedOutputStream);
        }
    }

    @Override
    public void writeToFile(@NonNull Reader reader) {
        writeToFile(reader, null, NONE, FLAG_SINGLE_MODE);
    }

    @Override
    public void writeToFile(@NonNull Reader reader, @NonNull File uniqueName) {
        writeToFile(reader, uniqueName, NONE, FLAG_NEW_FILE_NAME);
    }

    @Override
    public void writeToFile(@NonNull Reader reader, int size) {
        writeToFile(reader, null, size, FLAG_MULTI_MODE);
    }

    protected final void writeToFile(@NonNull Reader reader, @Nullable File uniqueName, int size, int flag) {
        //noinspection ConstantConditions
        if (null == reader) {
            Debug.e(String.format("the reader parameters(%s) cannot be null.", reader));
            throw new IllegalArgumentException("the reader parameters cannot be null.");
        }
        if (flag == FLAG_MULTI_MODE) {
            if (size <= 0) {
                Debug.e(String.format("the size parameters(%s) cannot be less than or equal to zero.", size));
                throw new IllegalArgumentException("the size parameters cannot be less than or equal to zero.");
            }
        }
        char[] chars = new char[DEFAULT_SIZE];
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        File file;
        if (flag == FLAG_NEW_FILE_NAME) {
            //noinspection ConstantConditions
            file = getNewFile(uniqueName);
        } else {
            file = getNewLogFile();
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true), DEFAULT_SIZE);
            bufferedReader = new BufferedReader(reader, DEFAULT_SIZE);
            int count;
            while (EOF != (count = bufferedReader.read(chars))) {
                bufferedWriter.write(chars, 0, count);
                bufferedWriter.flush();
                if (flag == FLAG_MULTI_MODE) {
                    if (file.length() > size) {
                        file = getNewLogFile();
                        bufferedWriter = new BufferedWriter(new FileWriter(file, true), DEFAULT_SIZE);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Debug.e(String.format("the file not found exception(%s) occurred when writing data to file.", e.getMessage()));
            e.printStackTrace();
        } catch (IOException e) {
            Debug.e(String.format("the io exception(%s) occurred when writing data to file.", e.getMessage()));
            e.printStackTrace();
        } finally {
            closeQuietly(reader);
            closeQuietly(bufferedReader);
            closeQuietly(bufferedWriter);
        }
    }

    @Override
    public File getNewFile(@NonNull String uniqueName) {
        File file;
        if (TextUtils.isEmpty(uniqueName)) {
            String msg = String.format("the current file name(%s) is not valid.", uniqueName);
            Debug.w(msg);
            throw new IllegalArgumentException(msg);
        } else {
            file = new File(uniqueName);
        }
        if (file.exists()) {
            Debug.i(String.format("the current file(%s) already exists.", file.getName()));
        } else {
            if (file.mkdir()) {
                Debug.i(String.format("the current new file is %s.", file.getName()));
            } else {
                Debug.w(String.format("no permission to create a %s file.", file.getName()));
            }
        }
        return file;
    }

    public File getNewFile(@NonNull File uniqueName) {
        File file;
        if (validateValue(uniqueName)) {
            String msg = String.format("the current parameter(%s) cannot be null.", "uniqueName");
            Debug.w(msg);
            throw new IllegalArgumentException(msg);
        } else {
            file = uniqueName;
        }
        if (file.exists()) {
            Debug.i(String.format("the current file(%s) already exists.", file.getName()));
        } else {
            if (file.mkdir()) {
                Debug.i(String.format("the current new file is %s.", file.getName()));
            } else {
                Debug.w(String.format("no permission to create a %s file.", file.getName()));
            }
        }
        return file;
    }

    public File getNewFile(@NonNull File parent, @NonNull String uniqueName) {
        if (validateValue(parent)) {
            String msg = String.format("the current parameter(%s) cannot be null.", "parent");
            Debug.w(msg);
            throw new IllegalArgumentException(msg);
        }
        if (validateValue(uniqueName)) {
            String msg = String.format("the current parameter(%s) cannot be null.", "uniqueName");
            Debug.w(msg);
            throw new IllegalArgumentException(msg);
        }
        File file = new File(parent, uniqueName);
        if (file.exists()) {
            Debug.i(String.format("the current file(%s) already exists.", file.getName()));
        } else {
            if (file.mkdir()) {
                Debug.i(String.format("the current new file is %s.", file.getName()));
            } else {
                Debug.w(String.format("no permission to create a %s file.", file.getName()));
            }
        }
        return file;
    }

    protected File getNewLogFile() {
        return getNewFile(getBaseDir(getContext(), LOG), getFormatFileName());
    }

    public String getFormatFileName(@Nullable String pattern, @NonNull String format) {
        if (TextUtils.isEmpty(pattern)) {
            return String.format("%s", format);
        } else {
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(pattern);
            return String.format(format, dateFormat.format(new Date()));
        }
    }

    protected String getFormatFileName() {
        return getFormatFileName(IFormat.LOG_PATTERN, IFormat.LOG_FORMAT);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected final ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 3,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadPoolExecutor.DiscardPolicy() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            super.rejectedExecution(r, e);
            Debug.w(String.format("too many task threads are executed and the current task threads(%s) are discarded.", r));
        }
    });

    public void submit(Runnable task) {
        executorService.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }


    public boolean validateValue(@NonNull String value) {
        return TextUtils.isEmpty(value);
    }

    public boolean validateValue(Object value) {
        return null == value;
    }
}
