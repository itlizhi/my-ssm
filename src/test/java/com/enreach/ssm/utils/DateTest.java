package com.enreach.ssm.utils;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.time.*;
import java.util.Date;

public class DateTest {

    @Test
    public void localDate() {

        //https://www.mkyong.com/java8/java-8-period-and-duration-examples/

        DateUtil.isLeapYear(null);

        Date d = new Date();
        System.out.println(d);

        LocalDate localDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate);

        LocalDateTime localDateTime = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(localDateTime);


        LocalDateTime dateTime =LocalDateTime.now();
        LocalDateTime dateTime1 = LocalDateTime.of(2017,2,12,1,1,1);

        Duration duration = Duration.between(dateTime1,dateTime);

        System.out.println(duration.getSeconds());


        LocalDate oldDate = LocalDate.of(2016, Month.OCTOBER, 10);
        LocalDate newDate = LocalDate.of(2018, Month.OCTOBER, 9);

        Period period = Period.between(oldDate, newDate);
        System.out.print(period.getYears() + " years,");
        System.out.print(period.getMonths() + " months,");
        System.out.print(period.getDays() + " days");

    }

}
