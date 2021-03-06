package com.dvsnier.cache.wrapper;

import com.dvsnier.cache.base.Closable;
import com.dvsnier.cache.base.ICacheSession;
import com.dvsnier.cache.transaction.CacheTransactionSession;
import com.dvsnier.cache.transaction.IGetCacheTransactionSession;

/**
 * ICacheWrapper
 * Created by dovsnier on 2019-07-10.
 */
public interface ICacheWrapper extends ICacheSession, IGetCacheTransactionSession<CacheTransactionSession>,
        Closable {
}
