package com.urise.webapp;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainDate {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - start);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println(calendar.getTime());

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.of (localDate, localTime);

        System.out.println(localDateTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY + MM + DD");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("DD.MM.YY");
        System.out.println(simpleDateFormat.format(date));
        System.out.println(simpleDateFormat2.format(date));


        System.out.println("******************");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("DD.MM.YY");
        System.out.println(dateTimeFormatter.format(localDateTime));

    }
}
