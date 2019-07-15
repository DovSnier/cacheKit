package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;

import java.io.InputStream;

/**
 * ICacheTransactionSession
 * Created by dovsnier on 2019-07-12.
 */
@Multiple
public interface ICacheTransactionSession<T> extends ICacheTransaction<T>, ICacheMultipleTransaction<T> {

    @Override
    T putString(@NonNull String type, @NonNull String key, String value);

    @Override
    T putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream);

    @Override
    T putObject(@NonNull String type, @NonNull String key, Object value);

    @Override
    String getString(@NonNull String type, @NonNull String key);

    @Override
    InputStream getInputStream(@NonNull String type, @NonNull String key);

    @Override
    Object getObject(@NonNull String type, @NonNull String key);

    @Override
    T put(@NonNull String type, @NonNull String key, Object value);

    @Override
    Object get(@NonNull String type, @NonNull String key);

    @Override
    T remove(@NonNull String type, @NonNull String key);

    @Override
    boolean commit(@NonNull String type);

    @Override
    T putString(@NonNull String key, String value);

    @Override
    T putInputStream(@NonNull String key, InputStream inputStream);

    @Override
    T putObject(@NonNull String key, Object value);

    @Override
    String getString(@NonNull String key);

    @Override
    InputStream getInputStream(@NonNull String key);

    @Override
    Object getObject(@NonNull String key);

    @Override
    T put(@NonNull String key, Object value);

    @Override
    Object get(@NonNull String key);

    @Override
    T remove(@NonNull String key);

    @Override
    boolean commit();
}
