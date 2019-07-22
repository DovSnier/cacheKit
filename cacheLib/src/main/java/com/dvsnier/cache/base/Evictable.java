package com.dvsnier.cache.base;

import android.support.annotation.NonNull;

import com.dvsnier.cache.config.Type;

/**
 * Evictable
 * Created by dovsnier on 2019-07-11.
 */
public interface Evictable extends AbstractEvitable {

    /**
     * the clean up the cache space with specified type
     *
     * @param type {@link Type}
     * @return true the clean up the cache space to complete otherwise no
     */
    boolean evict(@NonNull Type type);
}
