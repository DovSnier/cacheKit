package com.dvsnier.cache.config;

/**
 * IAlias
 * Created by dovsnier on 2019-07-11.
 */
public interface IAlias {

    /**
     * the cache engine instance alias
     *
     * @return {@see String}
     */
    String getAlias();


    /**
     * the settings alias of the cache engine instance
     *
     * @param alias {@see String}
     */
    void setAlias(String alias);
}
