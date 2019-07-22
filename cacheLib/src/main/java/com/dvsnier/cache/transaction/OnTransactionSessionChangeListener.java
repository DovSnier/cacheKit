package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dvsnier.cache.base.IBaseOnChangeListener;
import com.dvsnier.cache.config.IType;

/**
 * OnTransactionSessionChangeListener
 * Created by dovsnier on 2019-07-15.
 */
public interface OnTransactionSessionChangeListener extends IBaseOnChangeListener {

    /**
     * the monitor transaction session change notification
     *
     * @param alias {@link IType}
     * @param key   the key
     * @param value the value
     */
    void onTransactionSessionChange(@NonNull String alias, @NonNull String key, @Nullable Object value);
}
