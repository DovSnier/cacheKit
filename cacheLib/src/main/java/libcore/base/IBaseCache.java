package libcore.base;

/**
 * IBaseCache
 * Created by dovsnier on 2019-07-03.
 */
public interface IBaseCache extends IAbstractCache {

    /**
     * the default is 512m.
     */
    long DEFAULT_MAX_SIZE = 512 * 1024 * 1024;
}
