package com.dvsnier.cache.infrastructure;

import java.io.File;

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

    /**
     * the delete file or directory
     *
     * @param file the specified file
     */
    public void deleteFiles(File file) {
        if (null != file && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File itemFile : files) {
                    deleteFiles(itemFile);
                }
            }
            if (!file.delete()) {
                Debug.e(String.format("the failed to delete the specified file(%s).", file.getAbsolutePath()));
            }
        }
    }

    /**
     * to get folder size
     *
     * @param file the current file or directory
     * @return the file or directory size
     * @throws {@see Exception}
     */
    public long getFileSize(File file) {
        long size = 0;
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isDirectory()) {
                size += getFileSize(listFiles[i]);
            } else {
                size += listFiles[i].length();
            }
        }
        return size;
    }
}
