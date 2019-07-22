package com.dvsnier.cache.config;

import android.support.annotation.NonNull;

import com.dvsnier.cache.transaction.ITransaction;

/**
 * ITransactionAlias
 * Created by dovsnier on 2018/6/12.
 */
public interface ITransactionAlias {

    /**
     * to get cache instance
     *
     * @param type {@link Type}
     * @return {@link ITransaction}
     */
    ITransaction getCacheTransaction(@NonNull Type type);
}
