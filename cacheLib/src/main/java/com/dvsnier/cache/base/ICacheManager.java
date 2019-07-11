package com.dvsnier.cache.base;

import com.dvsnier.cache.transaction.ICacheMultipleTransaction;
import com.dvsnier.cache.transaction.ICacheTransaction;

import libcore.base.ICachePool;

/**
 * ICacheManager
 * Created by dovsnier on 2019-07-10.
 */
public interface ICacheManager extends IAbstractCacheManager, ICache, ICacheTransaction, ICacheMultipleTransaction, ICachePool {
}
