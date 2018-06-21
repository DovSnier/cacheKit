package com.dvsnier.cache.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheUtil {

    /**
     * @param context    {@see Context}
     * @param uniqueName disk cache directory name
     * @return {@see File}
     */
    File getDiskCacheDir(@NonNull Context context, @NonNull String uniqueName);
}
