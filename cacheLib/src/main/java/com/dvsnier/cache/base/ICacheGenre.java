package com.dvsnier.cache.base;

import libcore.base.IDiskLruCache;
import libcore.base.ILruCache;

/**
 * ICacheGenre
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheGenre {

    /**
     * to get lru cache instance
     *
     * @return {@link ILruCache}
     */
    ILruCache<String, Object> getCache();

    /**
     * the set lru cache instance
     *
     * @param lruCache {@link ILruCache}
     */
    void setCache(ILruCache<String, Object> lruCache);

    /**
     * to get disk lru cache instance
     *
     * @return {@link IDiskLruCache}
     */
    IDiskLruCache getDiskCache();

    /**
     * the set disk lru cache instance
     *
     * @param diskLruCache {@link IDiskLruCache}
     */
    void setDiskCache(IDiskLruCache diskLruCache);
}
