package com.enreach.ssm.utils;

import com.enreach.ssm.infrastructure.BizException;

import java.util.Collection;
import java.util.Map;

/**
 * 检查string list，map 是否为空
 * Array 使用 ArrayUtils 类库
 */
public class CheckUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }


    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }


    /**
     * 如果expression 为false，则抛出 BizException 错误
     *
     * @param expression
     * @param errorMessage
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new BizException(String.valueOf(errorMessage));
        }
    }

}
