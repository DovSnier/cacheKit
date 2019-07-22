package com.dvsnier.cache.base;

/**
 * AbstractEvitable
 * Created by dovsnier on 2019-07-11.
 */
public interface AbstractEvitable {

    /**
     * the clean up the cache space
     */
    void evictAll();
}
