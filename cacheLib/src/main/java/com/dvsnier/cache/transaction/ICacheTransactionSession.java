package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;

import java.io.InputStream;

/**
 * ICacheTransactionSession
 * Created by dovsnier on 2019-07-12.
 */
@Multiple
@Scheduled
public interface ICacheTransactionSession<T> extends ICacheTransaction<T>, ICacheScheduledTransaction<T>, ICacheMultipleTransaction<T>, ICacheMultipleScheduledTransaction<T> {

    @Multiple
    @Scheduled
    @Override
    T putString(@NonNull String type, @NonNull String key, String value, long duration, TimeUnit timeUnit);

    @Multiple
    @Scheduled
    @Override
    T putInputStream(@NonNull String type, @NonNull String key, InputStream inputStream, long duration, TimeUnit timeUnit);

    @Multiple
    @Scheduled
    @Override
    T putObject(@NonNull String type, @NonNull String key, Object value, long duration, TimeUnit timeUnit);

    @Multiple
    @Scheduled
    @Override
    T put(@NonNull String type, @NonNull String key, Object value, long duration, TimeUnit timeUnit);

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

    @Scheduled
    @Override
    T putString(@NonNull String key, String value, long duration, TimeUnit timeUnit);

    @Scheduled
    @Override
    T putInputStream(@NonNull String key, InputStream inputStream, long duration, TimeUnit timeUnit);

    @Scheduled
    @Override
    T putObject(@NonNull String key, Object value, long duration, TimeUnit timeUnit);

    @Scheduled
    @Override
    T put(@NonNull String key, Object value, long duration, TimeUnit timeUnit);

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
