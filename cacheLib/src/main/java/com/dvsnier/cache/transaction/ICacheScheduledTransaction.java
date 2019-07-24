package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;

import java.io.InputStream;

/**
 * ICacheScheduledTransaction
 * Created by dovsnier on 2018/6/12.
 */
@Scheduled
public interface ICacheScheduledTransaction<T> extends IAbstractScheduleTransaction<T> {

    /**
     * persistent string objects
     *
     * @param key      the current key
     * @param value    the current value
     * @param duration the duration
     * @param timeUnit {@link TimeUnit}
     * @return {@link ITransaction}
     */
    @Scheduled
    T putString(@NonNull String key, String value, long duration, TimeUnit timeUnit);

    /**
     * persistent stream objects
     *
     * @param key         the current key
     * @param inputStream the current value
     * @param duration    the duration
     * @param timeUnit    {@link TimeUnit}
     * @return {@link ITransaction}
     */
    @Scheduled
    T putInputStream(@NonNull String key, InputStream inputStream, long duration, TimeUnit timeUnit);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key      the current key
     * @param value    the current value
     * @param duration the duration
     * @param timeUnit {@link TimeUnit}
     * @return {@link T}
     */
    @Scheduled
    T putObject(@NonNull String key, Object value, long duration, TimeUnit timeUnit);

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
