package com.dvsnier.cache.base;

import libcore.base.IDiskLruGenre;
import libcore.base.ILruGenre;

/**
 * ICacheGenre
 * Created by dovsnier on 2018/6/12.
 */
public interface ICacheGenre {

    /**
     * to get lru cache instance
     *
     * @return {@link ILruGenre}
     */
    ILruGenre<String, Object> getCache();

    /**
     * the set lru cache instance
     *
     * @param lruCache {@link ILruGenre}
     */
    void setCache(ILruGenre<String, Object> lruCache);

    /**
     * to get disk lru cache instance
     *
     * @return {@link IDiskLruGenre}
     */
    IDiskLruGenre getDiskCache();

    /**
     * the set disk lru cache instance
     *
     * @param diskLruCache {@link IDiskLruGenre}
     */
    void setDiskCache(IDiskLruGenre diskLruCache);
}
