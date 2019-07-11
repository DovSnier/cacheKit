package com.dvsnier.cache.config;

import android.content.Context;

import com.dvsnier.cache.infrastructure.Debug;
import com.dvsnier.cache.infrastructure.Level;

import java.io.File;

/**
 * ICacheConfig
 * Created by dovsnier on 2016/9/26.
 */
public interface ICacheConfig extends IConfig {

    /**
     * the current application version code(or version name)
     *
     * @return version number
     */
    int getAppVersion();

    /**
     * the current max size of memory
     *
     * @return the appropriated memory size
     */
    int getCacheMaxSizeOfMemory();

    /**
     * the current max size of disk
     *
     * @return the appropriated disk size
     */
    long getCacheMaxSizeOfDisk();

    /**
     * the default value is one
     *
     * @return to vaule count
     */
    int getValueCount();

    /**
     * the current application that is cache directory of default
     *
     * @return {@link File}
     */
    File getCacheDirectory();

    /**
     * the get the multi-instance cache root directory
     *
     * @return {@see String}
     */
    String getUniqueName();

    /**
     * the setting up SDK operation mode
     *
     * @return false is default, otherwise true that is debug model.
     */
    boolean isDebug();

    /**
     * the log priority
     *
     * @return {@see Level}
     */
    Level getLevel();

    public class Builder implements ICacheConfig {

        private Context context;
        private int appVersion = 1;
        private int valueCount = 1;
        private int cacheMaxSizeOfMemory;
        private long cacheMaxSizeOfDisk;
        private File cacheDirectory;
        private String uniqueName;
        private boolean debug;
        private Level level;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setAppVersion(int appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder setValueCount(int valueCount) {
            this.valueCount = valueCount;
            return this;
        }

        public Builder setCacheMaxSizeOfMemory(int cacheMaxSizeOfMemory) {
            this.cacheMaxSizeOfMemory = cacheMaxSizeOfMemory;
            return this;
        }

        public Builder setCacheMaxSizeOfDisk(long cacheMaxSizeOfDisk) {
            this.cacheMaxSizeOfDisk = cacheMaxSizeOfDisk;
            return this;
        }

        public Builder setCacheDirectory(File cacheDirectory) {
            this.cacheDirectory = cacheDirectory;
            return this;
        }

        public Builder setUniqueName(String uniqueName) {
            this.uniqueName = uniqueName;
            return this;
        }

        public Builder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder setLevel(Level level) {
            this.level = level;
            return this;
        }

        @Override
        public Context getContext() {
            return context;
        }

        @Override
        public int getAppVersion() {
            return appVersion;
        }

        @Override
        public int getValueCount() {
            return valueCount;
        }

        @Override
        public int getCacheMaxSizeOfMemory() {
            return cacheMaxSizeOfMemory;
        }

        @Override
        public long getCacheMaxSizeOfDisk() {
            return cacheMaxSizeOfDisk;
        }

        @Override
        public File getCacheDirectory() {
            return cacheDirectory;
        }

        @Override
        public String getUniqueName() {
            return uniqueName;
        }

        @Override
        public boolean isDebug() {
            return debug;
        }

        @Override
        public Level getLevel() {
            return level;
        }

        public ICacheConfig create() {
            Debug.INSTANCE().setDebug(isDebug());
            if (null != getLevel()) {
                Debug.INSTANCE().setLevel(getLevel());
            }
            return this;
        }

        @Override
        public String toString() {
            String value = "Builder{" +
                    "context=" + context +
                    ", appVersion=" + appVersion +
                    ", valueCount=" + valueCount +
                    ", cacheMaxSizeOfMemory=" + cacheMaxSizeOfMemory +
                    ", cacheMaxSizeOfDisk=" + cacheMaxSizeOfDisk +
                    ", cacheDirectory=" + cacheDirectory +
                    ", uniqueName='" + uniqueName + '\'' +
                    ", debug=" + debug +
                    ", level=" + level +
                    '}';
            Debug.i(value);
            return value;
        }
    }
}
