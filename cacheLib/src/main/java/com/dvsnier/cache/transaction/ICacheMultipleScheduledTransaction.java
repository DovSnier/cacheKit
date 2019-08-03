package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.config.IType;

import java.io.InputStream;

/**
 * ICacheMultipleScheduledTransaction
 * Created by dovsnier on 2019-07-10.
 */
@Multiple
@Scheduled
public interface ICacheMultipleScheduledTransaction<T> extends IAbstractMultipleScheduledTransaction<T> {

    /**
     * persistent string objects
     *
     * @param type     {@link IType}
     * @param key      the current key
     * @param value    the current value
     * @param duration the duration
     * @param timeUnit {@link TimeUnit}
     * @return {@link ITransaction}
     */
    @Scheduled
    T putString(@NonNull String type, @NonNull String key, String value, long duration, TimeUnit timeUnit);

    /**
     * persistent stream objects
     *
     * @param type        {@link IType}
     * @param key         the current key
     * @param inputStream the current value
     * @param duration    the duration
     * @param timeUnit    {@link TimeUnit}
     * @return {@link ITransaction}
     */
    @Scheduled
    T putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream, long duration, TimeUnit timeUnit);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param type     {@link IType}
     * @param key      the current key
     * @param value    the current value
     * @param duration the duration
     * @param timeUnit {@link TimeUnit}
     * @return {@link ITransaction}
     */
    @Scheduled
    T putObject(@NonNull String type, @NonNull String key, Object value, long duration, TimeUnit timeUnit);

    /**
     * access to string persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     * @return {@see String}
     */
    String getString(@NonNull String type, @NonNull String key);

    /**
     * access to stream persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     * @return {@link InputStream}
     */
    InputStream getInputStream(@NonNull String type, @NonNull String key);

    /**
     * access to persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     * @return {@see Object}
     */
    Object getObject(@NonNull String type, @NonNull String key);

}
