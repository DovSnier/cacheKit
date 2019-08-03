package com.dvsnier.cache.transaction;

import com.dvsnier.cache.annotation.Internal;

/**
 * AbstractCacheTransactionSession
 * Created by dovsnier on 2019-07-26.
 */
public abstract class AbstractCacheTransactionSession<T> implements ICacheTransactionSession<T> {

    protected String alias;
    protected ITransaction<T> internalTransactionListener;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Internal
    protected ITransaction<T> getTransactionListener() {
        return internalTransactionListener;
    }

    @Internal
    protected void setTransactionListener(ITransaction<T> transaction) {
        this.internalTransactionListener = transaction;
    }
}
