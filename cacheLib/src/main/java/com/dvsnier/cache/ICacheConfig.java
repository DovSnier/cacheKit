package com.dvsnier.cache;

import android.content.Context;

import java.io.File;

/**
 * Created by dovsnier on 2016/9/26.
 */

public interface ICacheConfig {

    /**
     * the current context
     *
     * @return {@link Context}
     */
    Context getContext();

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
     * @return
     */
    File getCacheDirectory();

    public class Builder implements ICacheConfig {

        Context context;
        int appVersion = 1;
        int valueCount = 1;
        int cacheMaxSizeOfMemory;
        long cacheMaxSizeOfDisk;
        File cacheDirectory;

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

        public ICacheConfig create() {
            return this;
        }
    }
}
