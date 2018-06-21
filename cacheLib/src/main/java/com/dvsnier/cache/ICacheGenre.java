package com.dvsnier.cache;

import libcore.io.DiskLruCache;
import libcore.io.LruCache;

/**
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheGenre {

    /**
     * to get lru cache instance
     *
     * @return {@link LruCache}
     */
    LruCache<String, Object> getCache();

    /**
     * to get disk lru cache instance
     *
     * @return {@link DiskLruCache}
     */
    DiskLruCache getDiskCache();
}
