package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.base.ICache;

/**
 * ITransaction
 * Created by dovsnier on 2019-07-09.
 */
public interface ITransaction {

    /**
     * <code>-1</code> if the end of the stream is reached.
     */
    int DEFAULT = -1;
    /**
     * DEFAULT_BUFFER_SIZE is used to determine the default buffer size
     */
    int DEFAULT_BUFFER_SIZE = 1024;
    /**
     * DEFAULT_INDEX is used to determine the default index
     */
    int DEFAULT_INDEX = 0;

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key   the current key
     * @param value the current value
     * @return {@link ICache}
     */
    ITransaction put(@NonNull String key, Object value);

    /**
     * access to persistent objects
     *
     * @param key the current key
     * @return {@link Object}
     */
    Object get(@NonNull String key);

    /**
     * remove persistent objects
     *
     * @param key the current key
     */
    ITransaction remove(@NonNull String key);

    /**
     * Commit your cache changes
     *
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean commit();
}
