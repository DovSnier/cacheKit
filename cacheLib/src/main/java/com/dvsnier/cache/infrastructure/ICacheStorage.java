package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * ICacheStorage
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheStorage {

    /**
     * the get cached data access directory
     *
     * @param context    {@see Context}
     * @param uniqueName disk cache directory name
     * @return {@see File}
     */
    File getDiskCacheDir(@NonNull Context context, @NonNull String uniqueName);


    /**
     * the get the cache root directory
     *
     * @param context {@see Context}
     * @return {@see File}
     */
    File getCacheDir(@NonNull Context context);

    /**
     * whether the tag is stored externally
     *
     * @return true is external storage otherwise no that is internal storage
     */
    boolean isExternalStorage();
}
