package com.dvsnier.cache.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ICacheAPI
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheAPI extends IApi {

    String SDK_VERSION_KEY = "cache_sdk_version";

    /**
     * the write device sdk information
     *
     * @param context {@see Context}
     */
    void onSdkCallback(@NonNull Context context);

}
