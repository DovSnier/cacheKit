package libcore.base;

/**
 * IBaseCache
 * Created by dovsnier on 2019-07-03.
 */
public interface IBaseCache extends ICache {

    /**
     * the default is 512m.
     */
    long DEFAULT_MAX_SIZE = 512 * 1024 * 1024;

    /**
     * the denominator of the maximum memory fraction of the default tag
     */
    long DEFAULT_MAX_DENOMINATOR_OF_MEMORY = 8;
}
