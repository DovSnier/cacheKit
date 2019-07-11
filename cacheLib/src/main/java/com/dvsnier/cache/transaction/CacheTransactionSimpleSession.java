package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import java.io.InputStream;

/**
 * CacheTransactionSimpleSession
 * Created by dovsnier on 2019-07-12.
 */
public interface CacheTransactionSimpleSession extends ICacheTransaction<CacheTransactionSimpleSession> {

    @Override
    CacheTransactionSimpleSession putString(@NonNull String key, String value);

    @Override
    CacheTransactionSimpleSession putInputStream(@NonNull String key, InputStream inputStream);

    @Override
    CacheTransactionSimpleSession putObject(@NonNull String key, Object value);

    @Override
    String getString(@NonNull String key);

    @Override
    InputStream getInputStream(@NonNull String key);

    @Override
    Object getObject(@NonNull String key);

    @Override
    CacheTransactionSimpleSession put(@NonNull String key, Object value);

    @Override
    Object get(@NonNull String key);

    @Override
    CacheTransactionSimpleSession remove(@NonNull String key);

    @Override
    boolean commit();
}
