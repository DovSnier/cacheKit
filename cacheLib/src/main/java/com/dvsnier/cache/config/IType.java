package com.dvsnier.cache.config;

/**
 * IType
 * Created by dovsnier on 2019-07-03.
 */
public interface IType {

    /**
     * the none
     */
    String TYPE_NONE = "none";
    /**
     * the default
     */
    String TYPE_DEFAULT = ".0";
    /**
     * the journal
     */
    String TYPE_JOURNAL = "journal";
    /**
     * the documents
     */
    String TYPE_DOCUMENTS = "documents";
    /**
     * the media
     */
    String TYPE_MEDIA = "media";
    /**
     * the downloads
     */
    String TYPE_DOWNLOADS = "downloads";
    /**
     * the https or http that is network infrastructure,and so on
     */
    String TYPE_HTTPS = "https";

    /**
     * Media
     */
    public interface Media extends IType {

        /**
         * the pictures
         */
        String TYPE_PICTURES = "picture";
        /**
         * the videos
         */
        String TYPE_VIDEOS = "video";
        /**
         * the audios
         */
        String TYPE_AUDIOS = "audio";
        /**
         * the binary
         */
        String TYPE_BINARY = "binary";
    }
}
