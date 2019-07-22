package com.dvsnier.cache.transaction;

/**
 * ITransaction
 * Created by dovsnier on 2019-07-09.
 */
public interface ITransaction<T> {

    /**
     * <code>-1</code> if the end of the stream is reached.
     */
    int DEFAULT = -1;
    /**
     * DEFAULT_BUFFER_SIZE is used to determine the default buffer size
     */
    int DEFAULT_BUFFER_SIZE = 1024;
    /**
     * DEFAULT_INDEX is used to determine the default index
     */
    int DEFAULT_INDEX = 0;
}
