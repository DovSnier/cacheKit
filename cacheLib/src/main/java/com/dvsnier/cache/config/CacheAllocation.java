package com.dvsnier.cache.config;

import com.dvsnier.cache.exception.CacheAllocationException;

/**
 * Created by dovsnier on 2018/6/12.
 */
public class CacheAllocation {

    private static CacheAllocation INSTANCE = new CacheAllocation();
    protected boolean API_INNER;
    @SuppressWarnings("WeakerAccess")
    protected OnCacheAllocationListener onCacheAllocationListener;

    public static CacheAllocation INSTANCE() {
        return INSTANCE;
    }

    private CacheAllocation() {
    }

    public final boolean ApiOfInner() {
        Boolean value;
        if (null != getOnCacheAllocationListener()) {
            value = getOnCacheAllocationListener().obtainCacheAllocation();
            if (null == value) {
                throw new CacheAllocationException();
            } else {
                //noinspection UnnecessaryUnboxing
                return value.booleanValue();
            }
        }
        return OnCacheAllocationListener.DEFAULT_CACHE_ALLOCATION;
    }

    public OnCacheAllocationListener getOnCacheAllocationListener() {
        return onCacheAllocationListener;
    }

    public void setOnCacheAllocationListener(OnCacheAllocationListener onCacheAllocationListener) {
        this.onCacheAllocationListener = onCacheAllocationListener;
    }
}
