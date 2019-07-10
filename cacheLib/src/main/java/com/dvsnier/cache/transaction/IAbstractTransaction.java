package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.base.ICache;

/**
 * IAbstractTransaction
 * Created by dovsnier on 2019-07-10.
 */
public interface IAbstractTransaction extends ITransaction {

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
