package libcore.base;

import java.util.Map;

/**
 * ICachePool
 * Created by dovsnier on 2019-07-03.
 */
public interface ICachePool extends IPool {

    /**
     * the default cache pool
     *
     * @return the default cache collection pool
     */
    Map<String, IBaseCache> getCachePool();

    /**
     * the default cache instance
     *
     * @return the default cache instance
     */
    IBaseCache getDefaultCache();

    /**
     * a specific type of cache instance based on type type
     *
     * @param type the {@see IType}
     * @return the cache instance
     */
    IBaseCache getCache(String type);
}
