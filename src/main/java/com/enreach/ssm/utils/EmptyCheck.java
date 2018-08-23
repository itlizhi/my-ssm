package com.enreach.ssm.utils;

import java.util.Collection;
import java.util.Map;

public class EmptyCheck {

    public static boolean isEmpty(String str) {
        return str == null || str == "";
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

}
