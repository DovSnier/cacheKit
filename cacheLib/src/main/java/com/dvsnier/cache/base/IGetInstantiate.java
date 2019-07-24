package com.dvsnier.cache.base;

import com.dvsnier.cache.annotation.Improvement;
import com.dvsnier.cache.annotation.Internal;

/**
 * IGetInstantiate
 * Created by dovsnier on 2019-07-11.
 *
 * @deprecated
 */
@Improvement
@Internal
public interface IGetInstantiate {

    /**
     * the getting cache engine
     *
     * @return {@link ICacheEngine}
     */
    ICacheEngine getInstantiate();
}
