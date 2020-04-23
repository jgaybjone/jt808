package com.avenger.jt808.util;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Classname DateTimeUtils
 * Description 日期时间工具类
 * Date 2019/9/29 4:46 下午
 * Created by Wang jun gang
 */
@UtilityClass
public class LocalDateTimeUtils {

    private static final ConcurrentMap<String, DateTimeFormatter> PATTERN_MAP = new ConcurrentHashMap<>();

    /**
     * 获取当前时间戳
     *
     * @return timestamp
     */
    public static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    //Date转换为LocalDateTime
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    //LocalDateTime转换为Date
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    //获取指定日期的毫秒
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //获取指定日期的秒
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * @param time    待格式化时间
     * @param pattern 时间格式，例如"yyyy-MM-dd"
     * @return string
     */
    public static String formatTime(LocalDateTime time, String pattern) {

        final DateTimeFormatter formatter = PATTERN_MAP.getOrDefault(pattern, DateTimeFormatter.ofPattern(pattern));

        PATTERN_MAP.putIfAbsent(pattern, formatter);

        return time.format(formatter);
    }

    /**
     * @param date    待格式化时间
     * @param pattern 时间格式，例如"yyyy-MM-dd"
     * @return string
     */
    public static String formatTime(Date date, String pattern) {

        final Instant instant = date.toInstant();
        final LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        final DateTimeFormatter formatter = PATTERN_MAP.getOrDefault(pattern, DateTimeFormatter.ofPattern(pattern));

        PATTERN_MAP.putIfAbsent(pattern, formatter);

        return time.format(formatter);
    }

    /**
     * @param date    待格式化时间
     * @param pattern 时间格式，例如"yyyy-MM-dd"
     * @return string
     */
    public static String formatTime(Timestamp timestamp, String pattern) {

        final LocalDateTime time = timestamp.toLocalDateTime();
        final DateTimeFormatter formatter = PATTERN_MAP.getOrDefault(pattern, DateTimeFormatter.ofPattern(pattern));

        PATTERN_MAP.putIfAbsent(pattern, formatter);

        return time.format(formatter);
    }

    public static Timestamp formDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 获取当前时间的指定格式
     *
     * @param pattern 时间格式，例如"yyyy-MM-dd"
     * @return str
     */
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit
     *
     * @param time   待格式化时间
     * @param number 增加的量
     * @param field  单位
     * @return 计算后的时间
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    //日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     *
     * @param startTime 起始
     * @param endTime   结束
     * @param field     单位(年月日时分秒)
     * @return ChronoUnit单位值
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
        return field.between(startTime, endTime);
    }

    //获取一天的开始时间，2017,7,22 00:00
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    //获取一天的结束时间，2017,7,22 23:59:59.999999999
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

    public static LocalDateTime parse(String format, String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, formatter);
    }


}
