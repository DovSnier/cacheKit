package com.dvsnier.cache.base;

import com.dvsnier.cache.annotation.Multiple;
import com.dvsnier.cache.config.IType;

/**
 * MultipleEvictable
 * Created by dovsnier on 2019-07-11.
 */
@Multiple
public interface MultipleEvictable extends AbstractEvitable {

    /**
     * the clean up the cache space
     *
     * @param type {@link IType}
     * @return true the clean up the cache space to complete otherwise no
     */
    boolean evict(String type);
}
