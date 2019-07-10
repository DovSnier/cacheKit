package com.dvsnier.cache.transaction;

/**
 * IGetCacheTransaction
 * Created by dovsnier on 2019-07-10.
 */
public interface IGetCacheTransaction {

    /**
     * the get cached transaction instances
     *
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction getTransaction();
}
