package com.enreach.ssm.utils;

import java.math.BigDecimal;

public class ConvertUtil {

    public static String toDef(Object str) {
        if (str == null) {
            return "";
        }
        return (String) str;
    }

    public static int toDef(Integer value) {
        return value == null ? 0 : value.intValue();
    }

    public static long toDef(Long value) {
        return value == null ? 0L : value.longValue();
    }

    public static float toDef(Float value) {
        return value == null ? 0f : value.floatValue();
    }

    public static double toDef(Double value) {
        return value == null ? 0d : value.doubleValue();
    }

    public static boolean toDef(Boolean value) {
        return value == null ? false : value.booleanValue();
    }


    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        try {
            return Integer.parseInt(toDef(obj));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public static long toBigInt(Object obj) {
        if (obj == null) {
            return 0L;
        }
        try {
            return Long.parseLong(toDef(obj));
        } catch (NumberFormatException ex) {
            return 0L;
        }
    }

    public static float toFloat(Object obj) {
        if (obj == null) {
            return 0f;
        }
        try {
            return Float.parseFloat(toDef(obj));
        } catch (NumberFormatException ex) {
            return 0f;
        }
    }

    public static double toDouble(Object obj) {
        if (obj == null) {
            return 0d;
        }
        try {
            return Double.parseDouble(toDef(obj));
        } catch (NumberFormatException ex) {
            return 0d;
        }
    }

    public static BigDecimal toDecimal(Object obj) {
        if (obj == null) {
            return BigDecimal.valueOf(0);
        }
        try {
            return new BigDecimal(toDef(obj));
        } catch (NumberFormatException ex) {
            return BigDecimal.valueOf(0);
        }
    }

    /**
     * string 转换为 enum
     */
    public static <T extends Enum<T>> T toEnum(Class<T> enumClass, String value) {
        if (value == null) {
            return null;
        }
        return Enum.valueOf(enumClass, value);
    }

}
