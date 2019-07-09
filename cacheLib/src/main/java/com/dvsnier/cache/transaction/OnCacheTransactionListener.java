package com.dvsnier.cache.transaction;

/**
 * OnCacheTransactionListener
 * Created by dovsnier on 2018/6/12.
 */
public interface OnCacheTransactionListener {

    /**
     * the cache transaction changed listener
     *
     * @param cacheTransaction {@see ICacheTransaction}
     */
    void onCacheTransactionChanged(ITransaction cacheTransaction);
}
