package com.dvsnier.cache.infrastructure;

/**
 * ILogStorage
 * Created by dovsnier on 2019-07-16.
 */
public interface ILogStorage {

    /**
     * the execute a specified sequence of tasks
     *
     * @param cmdArray cmd sequence
     */
    void exec(String[] cmdArray);
}
