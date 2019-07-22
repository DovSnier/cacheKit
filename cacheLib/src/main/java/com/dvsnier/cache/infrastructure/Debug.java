package com.dvsnier.cache.infrastructure;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Debug
 * Created by dovsnier on 2019-07-02.
 * <pre>
 *    Priority:      E > W > I > D > V
 *    Ordinal:       0   1   2   3   4
 * </pre>
 */
public class Debug implements IDebug {

    protected static boolean debug;
    protected static Level level;
    private static final Debug INSTANCE = new Debug();

    private Debug() {
        level = Level.VERBOSE;
    }

    public static Debug INSTANCE() {
        return INSTANCE;
    }

    @Override
    public void setDebug(boolean debug) {
        Debug.debug = debug;
    }

    @Override
    public boolean isDebug() {
        return Debug.debug;
    }

    @Override
    public void setLevel(Level level) {
        Debug.level = level;
    }

    @Override
    public Level hasLevel() {
        return Debug.level;
    }

    /**
     * @return tag information
     */
    public static String TAG() {
        return TAG;
    }

    /**
     * the verbose information
     */
    public static void v(String msg) {
        v(TAG(), msg);
    }

    /**
     * the verbose information
     */
    public static void v(@NonNull String tag, String msg) {
        if (Debug.INSTANCE().isDebug()) {
            if (Debug.INSTANCE().hasLevel().getValue() >= Level.VERBOSE.getValue()) {
                Log.v(tag, msg);
            }
        }
    }


    /**
     * the debug information
     */
    public static void d(String msg) {
        d(TAG(), msg);
    }

    /**
     * the debug information
     */
    public static void d(@NonNull String tag, String msg) {
        if (Debug.INSTANCE().isDebug()) {
            if (Debug.INSTANCE().hasLevel().getValue() >= Level.DEBUG.getValue()) {
                Log.d(tag, msg);
            }
        }
    }

    /**
     * the info information
     */
    public static void i(String msg) {
        i(TAG(), msg);
    }

    /**
     * the info information
     */
    public static void i(@NonNull String tag, String msg) {
        if (Debug.INSTANCE().isDebug()) {
            if (Debug.INSTANCE().hasLevel().getValue() >= Level.INFO.getValue()) {
                Log.i(tag, msg);
            }
        }
    }

    /**
     * the warn information
     */
    public static void w(String msg) {
        w(TAG(), msg);
    }

    /**
     * the warn information
     */
    public static void w(@NonNull String tag, String msg) {
        if (Debug.INSTANCE().isDebug()) {
            if (Debug.INSTANCE().hasLevel().getValue() >= Level.WARN.getValue()) {
                Log.w(tag, msg);
            }
        }
    }

    /**
     * the error information
     */
    public static void e(String msg) {
        e(TAG(), msg);
    }

    /**
     * the error information
     */
    public static void e(@NonNull String tag, String msg) {
        if (Debug.INSTANCE().isDebug()) {
            if (Debug.INSTANCE().hasLevel().getValue() >= Level.ERROR.getValue()) {
                Log.e(tag, msg);
            }
        }
    }
}
