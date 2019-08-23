package com.dvsnier.cache.infrastructure;

import java.io.File;
import java.io.IOException;

/**
 * IWrite
 * Created by dovsnier on 2019-08-22.
 */
public interface IWrite<T> {

    /**
     * the file
     *
     * @return {@see File}
     */
    File getFile();

    /**
     * the out put stream or writer
     *
     * @return {@link WriteType}
     */
    WriteType getWriteType();

    /**
     * the write operation
     *
     * @param writer
     * @return true: the success writing and needs flush otherwise no
     */
    boolean write(T writer) throws IOException;
}
