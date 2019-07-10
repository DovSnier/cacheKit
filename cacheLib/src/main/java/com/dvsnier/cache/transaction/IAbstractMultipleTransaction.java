package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.config.IType;

/**
 * IAbstractMultipleTransaction
 * Created by dovsnier on 2019-07-10.
 */
@Multiple
public interface IAbstractMultipleTransaction extends ITransaction {

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param type  {@link IType}
     * @param key   the current key
     * @param value the current value
     * @return {@link ITransaction}
     */
    ITransaction put(@NonNull String type, @NonNull String key, Object value);

    /**
     * access to persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     * @return {@link Object}
     */
    Object get(@NonNull String type, @NonNull String key);

    /**
     * remove persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     */
    ITransaction remove(@NonNull String type, @NonNull String key);

    /**
     * Commit your cache changes
     *
     * @param type {@link IType}
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean commit(@NonNull String type);
}
