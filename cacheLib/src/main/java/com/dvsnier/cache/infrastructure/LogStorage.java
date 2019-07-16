package com.dvsnier.cache.infrastructure;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * LogStorage
 * Created by dovsnier on 2019-07-16.
 */
public class LogStorage extends AbstractFileStorage implements ILogStorage {

    private static final LogStorage INSTANCE = new LogStorage();

    public static LogStorage INSTANCE() {
        return INSTANCE;
    }

    private LogStorage() {
    }


    @Override
    public void exec(String[] cmdArray) {
        java.lang.Process process = null;
        try {
            Debug.i(String.format("the current command to be executed is: %s", Arrays.toString(cmdArray)));
            process = Runtime.getRuntime().exec(cmdArray);
        } catch (IOException e) {
            Debug.e(String.format("the current virtual machine executes parameter table(%s) exception(%s).", Arrays.toString(cmdArray), e.getMessage()));
            e.printStackTrace();
        }
        if (null != process) {
            InputStream inputStream = process.getInputStream();
            if (null != inputStream) {
                writeToFile(inputStream, Double.valueOf(getFormatted(3, SCU.M)).intValue());
            }
        }
    }

    public void execute(@NonNull Context context) {
        setContext(context);
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //noinspection ConstantConditions
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
            if (runningAppProcessInfo.pid == pid) {
                processName = runningAppProcessInfo.processName;
            }
        }

        if (!TextUtils.isEmpty(processName)) {
            Debug.v(String.format("the current process id is %s, and the process name is %s.", pid, processName));
            final String[] cmdArray = {"logcat", "-v", "time"};
            submit(Executors.defaultThreadFactory().newThread(new Runnable() {
                @Override
                public void run() {
                    exec(cmdArray);
                }
            }));
        }
    }
}
