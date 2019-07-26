package libcore.compat;

import libcore.base.ILruGenre;

/**
 * ICompatLruCache
 * Created by dovsnier on 2019-07-09.
 */
public interface ICompatLruCache<K, V> extends IAbstractCompatLruCache, ILruGenre<K, V> {
}
