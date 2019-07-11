package com.dvsnier.cache.config;

import com.dvsnier.cache.annotation.Improvement;

/**
 * IEngineConfig
 * Created by dovsnier on 2019-07-12.
 */
public interface IEngineConfig extends IConfig {

    /**
     * whether to instantiate or not
     *
     * @return true the instantiated otherwise false
     */
    @Improvement
    boolean isInstantiate();

    /**
     * mark whether the engine is shut down properly
     *
     * @return true the closed otherwise false
     */
    @Improvement
    boolean isClose();
}
