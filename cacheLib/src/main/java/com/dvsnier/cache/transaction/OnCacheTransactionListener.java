package com.dvsnier.cache.transaction;

import com.dvsnier.cache.config.IType;

/**
 * OnCacheTransactionListener
 * Created by dovsnier on 2018/6/12.
 */
public interface OnCacheTransactionListener {

    /**
     * the cache transaction changed listener
     *
     * @param type             {@link IType}
     * @param cacheTransaction {@see ICacheTransaction}
     */
    void onCacheTransactionChanged(String type, ITransaction cacheTransaction);
}
