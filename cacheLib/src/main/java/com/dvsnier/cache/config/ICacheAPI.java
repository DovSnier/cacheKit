package com.dvsnier.cache.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheAPI {

    String SDK_FILE_NAME = "dvs_config";
    String SDK_VERSION_KEY = "cache_sdk_version";

    /**
     * the write device sdk information
     *
     * @param context {@see Context}
     */
    void onSdkCallback(@NonNull Context context);

}
