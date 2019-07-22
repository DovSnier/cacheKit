package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import java.io.InputStream;

/**
 * ICacheTransaction
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheTransaction<T> extends IAbstractTransaction<T> {

    /**
     * persistent string objects
     *
     * @param key   the current key
     * @param value the current value
     * @return {@link ITransaction}
     */
    T putString(@NonNull String key, String value);

    /**
     * persistent stream objects
     *
     * @param key         the current key
     * @param inputStream the current value
     * @return {@link ITransaction}
     */
    T putInputStream(@NonNull String key, InputStream inputStream);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key   the current key
     * @param value the current value
     * @return {@link T}
     */
    T putObject(@NonNull String key, Object value);

    /**
     * access to string persistent objects
     *
     * @param key the current key
     * @return {@see String}
     */
    String getString(@NonNull String key);

    /**
     * access to stream persistent objects
     *
     * @param key the current key
     * @return {@link InputStream}
     */
    InputStream getInputStream(@NonNull String key);

    /**
     * access to persistent objects
     *
     * @param key the current key
     * @return {@see Object}
     */
    Object getObject(@NonNull String key);

}
