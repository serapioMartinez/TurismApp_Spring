package com.radical3d.turismapp.TurismApp.utils;

import java.io.PrintWriter;
import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

public class CustomLogger extends AbstractAppender{

    public final PrintWriter printWriter;

    

    public CustomLogger(String name, Filter filter, Layout<? extends Serializable> layout,
            PrintWriter printWriter) {
        super(name,filter,layout,false, null);
        this.printWriter = printWriter;
    }



    @Override
    public void append(LogEvent event) {
        String message = this.getLayout().toSerializable(event).toString();
        printWriter.println(message);
    }

    @Override
    public boolean isFiltered(LogEvent event) {
        return false; // Allow all events to be logged
    }

}
