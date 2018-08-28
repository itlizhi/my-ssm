package com.enreach.ssm.utils;

import java.math.BigDecimal;

/**
 * safe convert,避免 NullPointerException 和 NumberFormatException
 */
public class ConvertUtil {

    public static String toDef(String str) {
        if (str == null) {
            return "";
        }
        return str;
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
        return toInt(obj, 0);
    }

    public static int toInt(Object obj, int def) {
        if (obj == null) {
            return def;
        }
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (NumberFormatException ex) {
            return def;
        }
    }

    public static long toBigInt(Object obj) {
        return toBigInt(obj, 0L);
    }

    public static long toBigInt(Object obj, long def) {
        if (obj == null) {
            return def;
        }
        try {
            return Long.parseLong(String.valueOf(obj));
        } catch (NumberFormatException ex) {
            return def;
        }
    }

    public static float toFloat(Object obj) {
        return toFloat(obj, 0f);
    }

    public static float toFloat(Object obj, float def) {
        if (obj == null) {
            return def;
        }
        try {
            return Float.parseFloat(String.valueOf(obj));
        } catch (NumberFormatException ex) {
            return def;
        }
    }


    public static double toDouble(Object obj) {
        return toDouble(obj, 0d);
    }

    public static double toDouble(Object obj, double def) {
        if (obj == null) {
            return def;
        }
        try {
            return Double.parseDouble(String.valueOf(obj));
        } catch (NumberFormatException ex) {
            return def;
        }
    }


    public static BigDecimal toDecimal(Object obj) {
        return toDecimal(obj, BigDecimal.valueOf(0));
    }

    public static BigDecimal toDecimal(Object obj, BigDecimal def) {
        if (obj == null) {
            return def;
        }
        try {
            return new BigDecimal(String.valueOf(obj));
        } catch (NumberFormatException ex) {
            return def;
        }
    }

    public static boolean toBoolean(Object obj) {
        return  toBoolean(obj,false);
    }

    public static boolean toBoolean(Object obj, boolean def) {
        if (obj == null) {
            return def;
        }
        String value = String.valueOf(obj).toLowerCase();
        switch (value) {
            case "1":
            case "true":
                return true;
            case "0":
            case "false":
                return false;
            default:
                return def;

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
