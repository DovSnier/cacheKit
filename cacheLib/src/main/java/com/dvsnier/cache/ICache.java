package com.dvsnier.cache;

import android.content.Context;
import android.support.v4.util.LruCache;

import java.io.InputStream;

import libcore.io.DiskLruCache;

/**
 * Created by dovsnier on 2016/9/26.
 */

public interface ICache {

    /**
     * to get lru cache instance
     *
     * @return {@link LruCache}
     */
    public LruCache getCache();

    /**
     * to get disk lru cache instance
     *
     * @return {@link DiskLruCache}
     */
    public DiskLruCache getDiskCache();

    /**
     * initializes the caching system
     *
     * @param context {@link Context}
     */
    public void initialize(Context context);

    /**
     * initializes the caching system
     *
     * @param cacheConfig {@link ICacheConfig}
     */
    public void initialize(ICacheConfig cacheConfig);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key   the current key
     * @param value the current value
     */
    public void put(String key, Object value);

    /**
     * persistent string objects
     *
     * @param key   the current key
     * @param value the current value
     */
    public void putString(String key, String value);

    /**
     * persistent stream objects
     *
     * @param key         the current key
     * @param inputStream the current value
     */
    public void putInputStream(String key, InputStream inputStream);

    /**
     * persistent objects
     * <br/>note: only the serialized objects and parcelable objects instance can be persisted to disk space
     *
     * @param key   the current key
     * @param value the current value
     */
    public void putObject(String key, Object value);

    /**
     * access to persistent objects
     *
     * @param key the current key
     * @return {@link Object}
     */
    public Object get(String key);

    /**
     * access to string persistent objects
     *
     * @param key the current key
     * @return {@link String}
     */
    public String getString(String key);

    /**
     * access to stream persistent objects
     *
     * @param key the current key
     * @return {@link InputStream}
     */
    public InputStream getInputStream(String key);

    /**
     * access to persistent objects
     *
     * @param key the current key
     * @return {@link Object}
     */
    public Object getObject(String key);

    /**
     * remove persistent objects
     *
     * @param key the current key
     */
    public void remove(String key);

    /**
     * terminal the caching system
     */
    public void close();
}
