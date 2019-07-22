package com.dvsnier.cache.infrastructure;

/**
 * IDebug
 * Created by dovsnier on 2019-07-02.
 */
public interface IDebug {

    String TAG = "dvs_cache_sdk";

    /**
     * the setting up SDK operation mode
     *
     * @param debug false is default, otherwise true that is debug model.
     */
    void setDebug(boolean debug);

    /**
     * check whether it's log mode or not
     *
     * @return true is debug mode, otherwise no
     */
    boolean isDebug();

    /**
     * the log priority
     *
     * @param level {@link Level}
     */
    void setLevel(Level level);

    /**
     * the get level
     *
     * @return {@link Level}
     */
    Level hasLevel();
}
