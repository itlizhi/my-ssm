package com.enreach.ssm.utils;


import org.junit.Test;

public class RandomTest {

    @Test
    public void random() {

        System.out.println(RandomUtil.randomNumeric(8));
        System.out.println(RandomUtil.randomNumeric(8, 20));


        System.out.println(RandomUtil.randomLetter(10));
        System.out.println(RandomUtil.randomLetter(10, 20));

        System.out.println(RandomUtil.randomAscii(20));
        System.out.println(RandomUtil.randomAscii(RandomUtil.secureRandom(), 25));
        System.out.println(RandomUtil.randomAscii(RandomUtil.threadLocalRandom(), 10, 20));

    }

}
