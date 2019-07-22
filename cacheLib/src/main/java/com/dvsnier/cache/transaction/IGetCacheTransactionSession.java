package com.dvsnier.cache.transaction;

import com.dvsnier.cache.annotation.Multiple;

/**
 * IGetCacheTransactionSession
 * Created by dovsnier on 2019-07-12.
 */
@Multiple
public interface IGetCacheTransactionSession<T> {

    /**
     * the get cached transaction instances
     *
     * @return {@link ITransaction}
     */
    T getTransaction();
}
