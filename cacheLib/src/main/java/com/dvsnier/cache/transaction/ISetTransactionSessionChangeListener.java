package com.dvsnier.cache.transaction;

/**
 * ISetTransactionSessionChangeListener
 * Created by dovsnier on 2019-07-16.
 */
public interface ISetTransactionSessionChangeListener<T> {

    /**
     * the set transaction session change listener
     *
     * @param onTransactionSessionChangeListener {@link OnTransactionSessionChangeListener}
     */
    void setOnTransactionSessionChangeListener(T onTransactionSessionChangeListener);
}
