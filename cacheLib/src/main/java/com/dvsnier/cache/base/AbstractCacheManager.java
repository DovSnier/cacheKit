package com.dvsnier.cache.base;

import com.dvsnier.cache.transaction.CacheTransactionSession;
import com.dvsnier.cache.transaction.ICacheTransactionSession;

/**
 * AbstractCacheManager
 * Created by dovsnier on 2019-07-12.
 */
public abstract class AbstractCacheManager implements ICacheManager, ICacheTransactionSession<CacheTransactionSession> {
}
