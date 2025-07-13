package com.squirrelly_app.file_manager.util;

import org.slf4j.Logger;

public final class LoggerUtil {

    private static final String NULL_STRING = "NULL";

    private static final String INVOKED_LOG_MESSAGE = "ID: %s SOURCE: %s INVOKED - %s";
    private static final String SUCCESS_LOG_MESSAGE = "ID: %s SOURCE: %s SUCCESS - %s";
    private static final String FAILURE_LOG_MESSAGE = "ID: %s SOURCE: %s FAILURE - %s";

    public static void invoked(Logger logger, String requestId, String source, String message) {

        if (logger.isInfoEnabled()) {
            String formattedMessage = message == null ? NULL_STRING : message.replace("\n", "").replace("\r", "");
            logger.info( String.format(INVOKED_LOG_MESSAGE, requestId, source, formattedMessage));
        }

    }

    public static void success(Logger logger, String requestId, String source, String message) {

        if (logger.isInfoEnabled()) {
            String formattedMessage = message == null ? NULL_STRING : message.replace("\n", "").replace("\r", "");
            logger.info( String.format(SUCCESS_LOG_MESSAGE, requestId, source, formattedMessage));
        }

    }

    public static void failure(Logger logger, String requestId, String source, String message) {

        if (logger.isErrorEnabled()) {
            String formattedMessage = message == null ? NULL_STRING : message.replace("\n", "").replace("\r", "");
            logger.error( String.format(FAILURE_LOG_MESSAGE, requestId, source, formattedMessage));
        }

    }

}
