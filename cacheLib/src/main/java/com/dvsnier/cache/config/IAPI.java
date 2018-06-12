package com.dvsnier.cache.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by dovsnier on 2018/6/12.
 */
public interface IAPI {

    /**
     * the interface provided by api
     *
     * @param context {@see Context}
     */
    void initialized(@NonNull Context context);

    /**
     * the interface provided by api
     *
     * @param context     {@see Context}
     * @param versionCode the version code
     */
    void initialized(@NonNull Context context, int versionCode);
}
