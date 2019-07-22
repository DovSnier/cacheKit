package com.dvsnier.cache.infrastructure;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * IStorage
 * Created by dovsnier on 2019-07-16.
 */
public interface IStorage {

    /**
     * the get the app storage root directory
     *
     * @param context {@see Context}
     * @return {@see File}
     */
    File getBaseDir(@NonNull Context context);

    /**
     * the get the specified directory under the app storage root directory
     *
     * @param context    {@see Context}
     * @param uniqueName name
     * @return {@see File}
     */
    File getBaseDir(@NonNull Context context, @NonNull String uniqueName);
}
