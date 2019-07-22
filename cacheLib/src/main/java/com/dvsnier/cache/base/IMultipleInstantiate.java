package com.dvsnier.cache.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.IType;

/**
 * IMultipleInstantiate
 * Created by dovsnier on 2019-07-10.
 */
@Multiple
public interface IMultipleInstantiate extends IInstantiate {


    /**
     * initializes the caching system
     *
     * @param type    {@link IType}
     * @param context {@link Context}
     */
    void initialize(@NonNull String type, @NonNull Context context);

    /**
     * initializes the caching system
     *
     * @param type        {@link IType}
     * @param cacheConfig {@link ICacheConfig}
     */
    void initialize(@NonNull String type, @NonNull ICacheConfig cacheConfig);
}
