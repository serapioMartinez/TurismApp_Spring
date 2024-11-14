package com.radical3d.turismapp.TurismApp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerHelper {
    private static Logger logger = LogManager.getLogger();

    public static void info(String message){
        logger.info(message);
    }
    public static void error(String message){
        logger.error(message);
    }

    public static void warn(String message){
        logger.warn(message);
    }
}
