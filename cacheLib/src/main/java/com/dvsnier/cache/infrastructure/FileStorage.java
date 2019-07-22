package com.dvsnier.cache.infrastructure;

/**
 * FileStorage
 * Created by dovsnier on 2019-07-17.
 */
public class FileStorage extends AbstractFileStorage {

    private static final FileStorage INSTANCE = new FileStorage();

    public static FileStorage INSTANCE() {
        return INSTANCE;
    }

    private FileStorage() {
    }
}
