package com.dvsnier.cache.config;

/**
 * ICacheEngineConfig
 * Created by dovsnier on 2019-07-12.
 */
public interface ICacheEngineConfig extends IEngineConfig {

    /**
     * marking the instantiation status of the current cache engine
     *
     * @param instantiate true the instantiated otherwise false
     */
    void setInstantiate(boolean instantiate);
}
