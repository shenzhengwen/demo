package com.example.demo.util;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class DateTimeUtils {


    private DateTimeUtils() {
    }

    public static final String ES_FORMAT_INDEX = "yyyy-MM-dd";
    public static final String MONTH = "yyyy-MM";
    public static final String YEAR = "yyyy";

    private static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern(ES_FORMAT_INDEX);
    private static final DateTimeFormatter dayFormatterV1 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(MONTH);
    private static final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

    }

    /**
     * 根据传入日期 获取当天的结束时间  2019-07-18 23:59:59
     *
     * @return
     */
    public static long getDayEndByDate(String date) {
        LocalDate beginDateTime = LocalDate.parse(date, dayFormatterV1);
        LocalDateTime todayEnd = LocalDateTime.of(beginDateTime, LocalTime.MAX);
        return getTimestampOfDateTime(todayEnd);
    }

    public static LocalDate getDayLocalDate(String date) {
        return LocalDate.parse(date, dayFormatterV1);
    }

    /**
     * 根据传入日期 获取当天的开始时间  2019-07-18 00:00:00
     *
     * @return
     */
    public static long getDayStartByDate(String date) {
        LocalDate beginDateTime = LocalDate.parse(date, dayFormatterV1);
        LocalDateTime todayEnd = LocalDateTime.of(beginDateTime, LocalTime.MIN);
        return getTimestampOfDateTime(todayEnd);
    }

    /**
     * 或者年月日
     *
     * @return
     */
    public static final String getYearMonthDay() {
        LocalDate localDate = LocalDate.now();
        return localDate.format(dayFormatter);
    }

    /**
     * 时间戳转localDate
     *
     * @return
     */
    public static final String getYearMonthDayV1(long timestamp) {
        LocalDate localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return localDate.format(dayFormatterV1);
    }

    /**
     * 或者年月日
     *
     * @return
     */
    public static final String getYearMonthDayV1() {
        LocalDate localDate = LocalDate.now();
        return localDate.format(dayFormatterV1);
    }

    /**
     * Date类型转LocalDate类型
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * @param mills
     * @return
     */
    public static String formatMills(Long mills) {
        if (mills == null) {
            return "0";
        }
        //格林位置时间
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(mills), ZoneId.of("UTC"));
        return localDateTime.format(DateTimeFormatter.ofPattern(ES_FORMAT_INDEX));
    }

    public static final String getPreMonth() {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusMonths(1);
        return localDate.format(monthFormatter);
    }


    /**
     * 或者当前时间
     *
     * @return
     */
    public static final long getLongCurrentTime() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 当前时间 静默 i 分钟
     *
     * @param longCurrentTime
     * @return
     */
    public static final long getDelay10Minutes(Long longCurrentTime, int i) {
        LocalDateTime dateTimeOfTimestamp = getDateTimeOfTimestamp(longCurrentTime);
        LocalDateTime plus = dateTimeOfTimestamp.plus(i, ChronoUnit.MINUTES);
        return getDateTimeLong(plus);
    }

    /**
     * 当前时间 静默 i 分钟
     *
     * @param longCurrentTime
     * @return
     */
    public static final long getDelayMonth(Long longCurrentTime, int i) {
        LocalDateTime dateTimeOfTimestamp = getDateTimeOfTimestamp(longCurrentTime);
        LocalDateTime plus = dateTimeOfTimestamp.plus(i, ChronoUnit.MONTHS);
        return getDateTimeLong(plus);
    }
    /**
     * 设备封禁一日
     *
     * @return
     */
    public static final long getDelay1day(long longCurrentTime,long i) {

        LocalDateTime dateTimeOfTimestamp = getDateTimeOfTimestamp(longCurrentTime);
        LocalDateTime plus = dateTimeOfTimestamp.plus(i, ChronoUnit.DAYS);
        return getDateTimeLong(plus);
    }

    /**
     * 当前时间 向后推 n 个小时 获取整点时间
     * @param n
     * @return
     */
    public final static Long getHour(int n) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime nHours = localDateTime.minusHours(n+1);
        return nHours.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 当前时间 向后推 n 个小时 获取整点时间
     * @param n
     * @return
     */
    public final static Long getMinute(int n) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime nHours = localDateTime.minusMinutes(n+1);
        return nHours.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 封禁一年
     *
     * @param longCurrentTime
     * @param i
     * @return
     */
    public static final long getDelay1Year(long longCurrentTime, int i) {

        LocalDateTime dateTimeOfTimestamp = getDateTimeOfTimestamp(longCurrentTime);
        LocalDateTime plus = dateTimeOfTimestamp.plus(i, ChronoUnit.YEARS);
        return getDateTimeLong(plus);
    }


    /**
     * locatlDatetime 转Long
     *
     * @param localDateTime
     * @return
     */
    public static final long getDateTimeLong(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 将long类型的timestamp转为LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 3.将LocalDateTime转为long类型的timestamppublic
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 将LocalDateTime转为自定义的时间格式的字符串
     *
     * @param localDateTime
     * @param format
     * @return
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }


    /**
     * 4.将某时间字符串转为自定义时间格式的LocalDateTime
     *
     * @param time
     * @param format
     * @return
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * 获取当天的开始时间 当天零点
     *
     * @return
     */
    public static long getDayBegin() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return getTimestampOfDateTime(todayStart);
    }

    /**
     * 获取当天的结束时间
     *
     * @return
     */
    public static long getDayEnd() {
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return getTimestampOfDateTime(todayEnd);
    }


}
