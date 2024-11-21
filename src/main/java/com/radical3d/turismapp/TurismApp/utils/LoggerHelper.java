package com.radical3d.turismapp.TurismApp.utils;

import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.layout.PatternLayout;



public class LoggerHelper {
    
    private static Logger logger = LogManager.getLogger(configurelogger());
    public final static PrintWriter printWriter = new PrintWriter(System.out,true);

    private static CustomLogger configurelogger(){
        Layout layout = PatternLayout.createDefaultLayout();
        return new CustomLogger("CustomLogger", null, layout, printWriter);
    }

    public static void info(String message){
        logger.info(message);
    }
    public static void error(Object error){
        logger.error(error);
    }
    public static void printStackTrace(StackTraceElement[] stackTrace){
        if (stackTrace == null) return;
        for (StackTraceElement stackTraceElement : stackTrace) {
            logger.error(stackTraceElement);
        }
    }
    public static void warn(String message){
        logger.warn(message);
    }
}
