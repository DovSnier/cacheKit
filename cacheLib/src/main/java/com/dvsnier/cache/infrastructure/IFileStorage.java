package com.dvsnier.cache.infrastructure;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * IFileStorage
 * Created by dovsnier on 2019-07-17.
 */
public interface IFileStorage {

    /* the end of file */
    int EOF = -1;
    /* the none */
    int NONE = 0;
    /* the line file mode that include input and output */
    int FLAG_LINE = 1;
    /* the custom an file name */
    int FLAG_NEW_FILE_NAME = FLAG_LINE + 1;
    /* the default block unit */
    int DEFAULT_SIZE = 1024;
    /* the single file mode */
    int FLAG_SINGLE_MODE = 2048;
    /* the multi file mode */
    int FLAG_MULTI_MODE = 4096;
    /* the default encoding format */
    Charset UTF_8 = Charset.forName("UTF-8");
    /* the log directory */
    String LOG = "log";

    /**
     * the reading text data from stream
     *
     * @param inputStream {@see InputStream}
     * @return the data of Char Sequence
     */
    CharSequence read(@NonNull InputStream inputStream);

    /**
     * the reading text data from stream
     *
     * @param reader {@see Reader}
     * @return the data of Char Sequence
     */
    CharSequence read(@NonNull Reader reader);

    /**
     * the write data to file
     *
     * @param inputStream {@see InputStream}
     */
    void writeToFile(@NonNull InputStream inputStream);

    /**
     * the write data to file
     *
     * @param inputStream {@see InputStream}
     * @param uniqueName  the unique file
     */
    void writeToFile(@NonNull InputStream inputStream, @NonNull File uniqueName);

    /**
     * the write data to file
     *
     * @param inputStream {@see InputStream}
     * @param size        the limit file size
     */
    void writeToFile(@NonNull InputStream inputStream, int size);

    /**
     * the write data to file
     *
     * @param reader {@see Reader}
     */
    void writeToFile(@NonNull Reader reader);

    /**
     * the write data to file
     *
     * @param reader     {@see Reader}
     * @param uniqueName the unique file
     */
    void writeToFile(@NonNull Reader reader, @NonNull File uniqueName);

    /**
     * the write data to file
     *
     * @param reader {@see Reader}
     * @param size   the limit file size
     */
    void writeToFile(@NonNull Reader reader, int size);

    /**
     * the create file
     *
     * @param uniqueName the unique file name
     * @return the new files currently created
     */
    File getNewFile(@NonNull String uniqueName);
}