package com.dvsnier.cache.config;

/**
 * Created by dovsnier on 2018/6/12.
 */
public interface OnCacheAllocationListener {

    /**
     * false that is default the cache allocation value
     */
    boolean DEFAULT_CACHE_ALLOCATION = false;

    /**
     * obtain the cache allocation
     *
     * @return {@see Boolean}
     */
    Boolean obtainCacheAllocation();
}
