package com.dvsnier.cache.base;

import libcore.base.IBaseCache;

/**
 * ICacheSession
 * Created by dovsnier on 2016/9/26.
 */
public interface ICacheSession extends IBaseCache, ISession {

    /**
     * the get cache session
     *
     * @return {@link ICacheSession}
     */
    ICacheSession getCacheSession();
}
