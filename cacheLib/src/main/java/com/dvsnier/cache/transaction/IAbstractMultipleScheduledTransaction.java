package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.config.IType;

/**
 * IAbstractMultipleScheduledTransaction
 * Created by dovsnier on 2019-07-10.
 */
@Multiple
@Scheduled
public interface IAbstractMultipleScheduledTransaction<T> extends ITransaction<T> {

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
    T put(@NonNull String type, @NonNull String key, Object value, long duration, TimeUnit timeUnit);

    /**
     * access to persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     * @return {@see Object}
     */
    Object get(@NonNull String type, @NonNull String key);

    /**
     * remove persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     */
    T remove(@NonNull String type, @NonNull String key);

    /**
     * Commit your cache changes
     *
     * @param type {@link IType}
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean commit(@NonNull String type);
}
