package com.github.jreddit.parser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    
    private JsonUtils() {
        // forbid creating JsonUtils instance
    }

    
    public static String safeJsonToString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    
    public static Integer safeJsonToInteger(Object obj) {
        Integer intValue = null;

        try {
            String str = safeJsonToString(obj);
            intValue = str != null ? Integer.parseInt(str) : null;
        } catch (NumberFormatException e) {
            LOGGER.warn("Safe JSON conversion to Integer failed", e);
        }

        return intValue;
    }
    
    
    public static Double safeJsonToDouble(Object obj) {
        Double doubleValue = null;

        try {
            String str = safeJsonToString(obj);
            doubleValue = str != null ? Double.parseDouble(str) : null;
        } catch (NumberFormatException e) {
            LOGGER.warn("Safe JSON conversion to Double failed", e);
        }

        return doubleValue;
    }
    
    
    
    public static Boolean safeJsonToBoolean(Object obj) {
        String str = safeJsonToString(obj);
        Boolean booleanValue = str != null ? Boolean.parseBoolean(str) : null;
        return booleanValue;
    }
    
    
    public static Long safeJsonToLong(Object obj) {
        Long longValue = null;

        try {
            String str = safeJsonToString(obj);
            longValue = str != null ? Long.parseLong(str) : null;
        } catch (NumberFormatException e) {
            LOGGER.warn("Safe JSON conversion to Long failed", e);
        }

        return longValue;
    }
    
}
