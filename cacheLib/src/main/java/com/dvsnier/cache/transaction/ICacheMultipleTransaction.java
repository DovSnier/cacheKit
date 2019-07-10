package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.config.IType;

import java.io.InputStream;

/**
 * ICacheMultipleTransaction
 * Created by dovsnier on 2019-07-10.
 */
@Multiple
public interface ICacheMultipleTransaction extends IAbstractMultipleTransaction {

    /**
     * persistent string objects
     *
     * @param type  {@link IType}
     * @param key   the current key
     * @param value the current value
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction putString(@NonNull String type, @NonNull String key, String value);

    /**
     * persistent stream objects
     *
     * @param type        {@link IType}
     * @param key         the current key
     * @param inputStream the current value
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param type  {@link IType}
     * @param key   the current key
     * @param value the current value
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction putObject(@NonNull String type, @NonNull String key, Object value);

    /**
     * access to string persistent objects
     *
     * @param type {@link IType}
     * @param key  the current key
     * @return {@link String}
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
     * @return {@link Object}
     */
    Object getObject(@NonNull String type, @NonNull String key);

}
