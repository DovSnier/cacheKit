package com.dvsnier.cache.infrastructure;

import java.io.File;

/**
 * SimpleWriter
 * Created by dovsnier on 2019-08-22.
 */
public abstract class SimpleWriter<T> implements IWrite<T> {

    protected File file;
    protected WriteType writeType;

    private SimpleWriter() {
    }

    public SimpleWriter(WriteType writeType) {
        this.writeType = writeType;
    }

    @Override
    public File getFile() {
        return file;
    }

    public SimpleWriter setFile(File file) {
        this.file = file;
        return this;
    }

    @Override
    public WriteType getWriteType() {
        return writeType;
    }

    protected void setWriteType(WriteType writeType) {
        this.writeType = writeType;
    }
}
