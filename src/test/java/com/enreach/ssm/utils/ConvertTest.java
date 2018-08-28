package com.enreach.ssm.utils;

import org.junit.Test;

public class ConvertTest {

    @Test
    public void toDef() {

        System.out.println(ConvertUtil.toDef("abc"));
        String object = null;
        System.out.println(ConvertUtil.toDef(object));

        Float f = null;
        System.out.println(ConvertUtil.toDef(12));

        System.out.println(0==ConvertUtil.toDef(f));

    }

    @Test
    public void convert() {

        System.out.println(ConvertUtil.toInt("abc"));
        System.out.println(ConvertUtil.toInt("123"));

        System.out.println(ConvertUtil.toFloat("abc"));
        System.out.println(ConvertUtil.toFloat("123"));
        System.out.println(ConvertUtil.toFloat("123.4"));

        System.out.println(ConvertUtil.toBigInt("abc"));
        System.out.println(ConvertUtil.toBigInt("123"));
        System.out.println(ConvertUtil.toBigInt("123.4"));

        System.out.println(ConvertUtil.toDouble(null));
        System.out.println(ConvertUtil.toDouble("abc"));
        System.out.println(ConvertUtil.toDouble("123"));
        System.out.println(ConvertUtil.toDouble("123.4"));


        System.out.println(ConvertUtil.toDecimal(null));
        System.out.println(ConvertUtil.toDecimal("abc"));
        System.out.println(ConvertUtil.toDecimal("123"));
        System.out.println(ConvertUtil.toDecimal("123.4"));

    }
}
