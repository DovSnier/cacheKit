package com.dvsnier.cache.transaction;

import com.dvsnier.cache.annotation.Hide;

/**
 * ISetCacheTransactionListener
 * Created by dovsnier on 2019-07-16.
 *
 * @deprecated
 */
@Hide
public interface ISetCacheTransactionListener<T> {

    /**
     * the set cache transaction listener
     *
     * @param onCacheTransactionListener {@link OnCacheTransactionListener}
     */
    void setOnCacheTransactionListener(T onCacheTransactionListener);
}
