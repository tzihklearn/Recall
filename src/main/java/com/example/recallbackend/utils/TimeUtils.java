package com.example.recallbackend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author tzih
 * @date 2022.09.21
 */
public class TimeUtils {

    //获取特定时区的时间戳
    public static Long getNowTime() {
        LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        return time.toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000;
    }


    //获取当日凌晨零点的时间

    /**
     * @return 当天零点的10位时间戳
     */
    public static Long getDayTime() {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return today_start.toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000;
    }
    //获取几天前的时间戳

    /**
     * @param day 天数
     * @return 几天前的零点的10位时间戳
     */
    public static Long getDayTimeBefore(Integer day) {
        day *= -1;
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now().plusDays(day), LocalTime.MIN);
        return today_start.toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000;

    }

    //将时间戳转换为时间

    /**
     * @param lt 10位时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String transform(long lt) {
        lt *= 1000L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date1 = new Date(lt);
        return simpleDateFormat.format(date1);
    }

    //将时间戳转换为时间，以MM.dd的形式

    /**
     * @param lt 10位时间戳
     * @return 以MM.dd的形式
     */
    public static String transformDay(long lt) {
        lt *= 1000L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date1 = new Date(lt);
        return simpleDateFormat.format(date1);
    }

    /**
     * @param lt 10位时间戳
     * @return 以 MM/dd HH:mm 的形式
     */
    public static String transformDayAndHour(long lt) {
        lt *= 1000L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date1 = new Date(lt);
        return simpleDateFormat.format(date1);
    }

    /**
     * @param lt 10位时间戳
     * @return 以 yyyy.MM.dd 的形式
     */
    public static String transformYearAndMonthAdnDay(long lt) {
        lt *= 1000L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date1 = new Date(lt);
        return simpleDateFormat.format(date1);
    }

    /**
     * @param lt 时间
     * @return 10位时间戳
     */
    public static String transformNotSeconds(long lt) {
        lt *= 1000L;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = new Date(lt);
        return df.format(date);
    }

    public static String transformHhMm(long lt) {
        lt *= 1000L;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = new Date(lt);
        return df.format(date);
    }

    public static String transformMmSs(long lt) {
        lt *= 1000L;
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = new Date(lt);
        return df.format(date);
    }

    /**
     * 要处理
     *
     * @param t yyyy-MM-dd HH:mm:ss
     * @return 10为时间戳
     */
    //TODO:记得处理
    public static Long transformTurnAll(String t) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        try {
            Date d1 = df.parse(t);
            return d1.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * @param t1 时间字符串
     * @param t2 时间字符串
     * @return mm:ss
     */
    public static String TimeSub(String t1, String t2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        try {
            Date d1 = df.parse(t1);
            Date d2 = df.parse(t2);
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
            return minutes + hours * 60 + ":" + seconds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param before 10位时间戳
     * @param end 10位时间戳
     * @return 返回天数
     */
    public static int TimeSubDay(long before, long end) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String time1 = df.format(new Date(before * 1000L));
        String time2 = df.format(new Date(end * 1000L));

        Date d1 = df.parse(time1);
        Date d2 = df.parse(time2);
        long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
        return Math.toIntExact(diff / (1000 * 60 * 60 * 24));


    }
}
