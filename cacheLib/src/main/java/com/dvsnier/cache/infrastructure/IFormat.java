package com.dvsnier.cache.infrastructure;

/**
 * IFormat
 * Created by dovsnier on 2019-07-17.
 */
public interface IFormat {

    /* the none */
    String NONE = "null";
    /* the underline */
    String UNDERLINE = "_";
    /* the temp pattern */
    String TEMP_PATTERN = "yyyyMMddHHmmss";
    /* the info pattern */
    String INFO_PATTERN = "yyyyMMddHHmm";
    /* the log pattern */
    String LOG_PATTERN = "yyyyMMdd_HHmmss_SSSS";
    /* the log format */
    String LOG_FORMAT = "log_%s.log";
    /* the info format */
    String INFO_FORMAT = "%s.info";
    /* the temp format */
    String TEMP_FORMAT = "%s.tmp";
}
