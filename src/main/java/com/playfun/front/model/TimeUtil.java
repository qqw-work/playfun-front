package com.playfun.front.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class TimeUtil {

    public static long timestamp() {
        return System.currentTimeMillis()/1000L;
    }

    public static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

    public static String today() {
        return LocalDate.now().toString();
    }

    public static String yesterday() {
        return todayPlus(-1);
    }

    public static String lastNDay(int n) {
        return todayPlus(n);
    }

    public static String last2Day() {
        return todayPlus(-2);
    }

    private static String todayPlus(int days) {
        LocalDate today = LocalDate.now();
        LocalDate result = today.plus(days, ChronoUnit.DAYS);

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(TimeUtil.timestamp());
        System.out.println(TimeUtil.today());
        System.out.println(TimeUtil.yesterday());
        System.out.println(TimeUtil.now());
    }
}
