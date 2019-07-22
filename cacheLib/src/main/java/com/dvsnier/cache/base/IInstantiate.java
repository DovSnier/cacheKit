package com.dvsnier.cache.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dvsnier.cache.config.ICacheConfig;

/**
 * IInstantiate
 * Created by dovsnier on 2019-07-05.
 */
public interface IInstantiate {

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
}
