package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import java.io.InputStream;

/**
 * CacheTransactionSession
 * Created by dovsnier on 2019-07-12.
 */
public interface CacheTransactionSession extends ICacheTransaction<CacheTransactionSession>, ICacheMultipleTransaction<CacheTransactionSession> {

    @Override
    CacheTransactionSession putString(@NonNull String type, @NonNull String key, String value);

    @Override
    CacheTransactionSession putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream);

    @Override
    CacheTransactionSession putObject(@NonNull String type, @NonNull String key, Object value);

    @Override
    String getString(@NonNull String type, @NonNull String key);

    @Override
    InputStream getInputStream(@NonNull String type, @NonNull String key);

    @Override
    Object getObject(@NonNull String type, @NonNull String key);

    @Override
    CacheTransactionSession put(@NonNull String type, @NonNull String key, Object value);

    @Override
    Object get(@NonNull String type, @NonNull String key);

    @Override
    CacheTransactionSession remove(@NonNull String type, @NonNull String key);

    @Override
    boolean commit(@NonNull String type);

    @Override
    CacheTransactionSession putString(@NonNull String key, String value);

    @Override
    CacheTransactionSession putInputStream(@NonNull String key, InputStream inputStream);

    @Override
    CacheTransactionSession putObject(@NonNull String key, Object value);

    @Override
    String getString(@NonNull String key);

    @Override
    InputStream getInputStream(@NonNull String key);

    @Override
    Object getObject(@NonNull String key);

    @Override
    CacheTransactionSession put(@NonNull String key, Object value);

    @Override
    Object get(@NonNull String key);

    @Override
    CacheTransactionSession remove(@NonNull String key);

    @Override
    boolean commit();
}
