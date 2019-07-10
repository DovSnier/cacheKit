package com.dvsnier.cache.wrapper;

import com.dvsnier.cache.base.ICache;
import com.dvsnier.cache.base.ICacheEngine;
import com.dvsnier.cache.transaction.IGetCacheTransaction;

/**
 * ICacheWrapper
 * Created by dovsnier on 2019-07-10.
 */
public interface ICacheWrapper extends ICacheEngine, ICache, IGetCacheTransaction {
}
