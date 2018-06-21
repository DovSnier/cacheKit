package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.ICache;

import java.io.InputStream;

/**
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheTransaction {

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
    ICacheTransaction put(@NonNull String key, Object value);

    /**
     * persistent string objects
     *
     * @param key   the current key
     * @param value the current value
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction putString(@NonNull String key, String value);

    /**
     * persistent stream objects
     *
     * @param key         the current key
     * @param inputStream the current value
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction putInputStream(@NonNull String key, InputStream inputStream);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key   the current key
     * @param value the current value
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction putObject(@NonNull String key, Object value);

    /**
     * access to persistent objects
     *
     * @param key the current key
     * @return {@link Object}
     */
    Object get(@NonNull String key);

    /**
     * access to string persistent objects
     *
     * @param key the current key
     * @return {@link String}
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
     * @return {@link Object}
     */
    Object getObject(@NonNull String key);

    /**
     * remove persistent objects
     *
     * @param key the current key
     */
    ICacheTransaction remove(@NonNull String key);

    /**
     * Commit your cache changes
     *
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean commit();
}
