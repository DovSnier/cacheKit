package com.dvsnier.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dvsnier.cache.config.ICacheConfig;

/**
 * Created by dovsnier on 2016/9/26.
 */

public interface ICache {

    /**
     * the default is 512m.
     */
    long DEFAULT_MAX_SIZE = 512 * 1024 * 1024;

    /**
     * initializes the caching system
     *
     * @param context {@link Context}
     */
    void initialize(@NonNull Context context);

    /**
     * initializes the caching system
     *
     * @param cacheConfig {@link ICacheConfig}
     */
    void initialize(@NonNull ICacheConfig cacheConfig);

    /**
     * terminal the caching system
     */
    void close();
}
