package com.dvsnier.cache.base;

import com.dvsnier.cache.annotation.Internal;

/**
 * ITransactionSession
 * Created by dovsnier on 2019-07-15.
 */
@Internal
public interface ITransactionSession<T> extends ISession {

    /**
     * the obtain transaction sessions
     *
     * @return {@link ITransactionSession}
     */
    T getTransactionSession();

    /**
     * setting up transaction sessions
     *
     * @param transactionSession {@link ITransactionSession}
     */
    void setTransactionSession(T transactionSession);
}
