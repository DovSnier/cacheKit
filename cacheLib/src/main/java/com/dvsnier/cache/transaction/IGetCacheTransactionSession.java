package com.dvsnier.cache.transaction;

/**
 * IGetCacheTransactionSession
 * Created by dovsnier on 2019-07-12.
 */
public interface IGetCacheTransactionSession<T> {

    /**
     * the get cached transaction instances
     *
     * @return {@link ITransaction}
     */
    T getTransaction();
}
