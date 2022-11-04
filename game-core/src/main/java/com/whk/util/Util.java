package com.whk.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Util {

    public static DateTimeFormatter getFormatter(){
        return new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").toFormatter();
    }

    public static DateTimeFormatter getFormatter1(){
        return new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss").toFormatter();
    }
}
