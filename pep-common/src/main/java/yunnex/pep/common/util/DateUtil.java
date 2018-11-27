package yunnex.pep.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类。java8日期格式化，与Date转换等。
 */
public abstract class DateUtil {

    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateFormat.DATE_TIME_LINE.formatter;

    /**
     * 日期格式
     */
    public enum DateFormat {

        /**
         * 日期格式
         */
        DATE_LINE("yyyy-MM-dd"),
        DATE_SLASH("yyyy/MM/dd"),
        DATE_NONE("yyyyMMdd"),

        /**
         * 日期时间格式
         */
        DATE_TIME_LINE("yyyy-MM-dd HH:mm:ss"),
        DATE_TIME_SLASH("yyyy/MM/dd HH:mm:ss"),
        DATE_TIME_NONE("yyyyMMdd HH:mm:ss"),

        /**
         * 时间戳格式
         */
        DATE_TIME_EMPTY("yyyyMMddHHmmss"),
        DATE_TIME_MILLI_EMPTY("yyyyMMddHHmmssSSS");

        private transient DateTimeFormatter formatter;

        DateFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }


    /**
     * String 转 LocalDate
     *
     * @param dateStr
     * @return
     */
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DateFormat.DATE_LINE.formatter);
    }

    /**
     * String 转 LocalDate
     *
     * @param dateStr
     * @param format 时间格式
     * @return
     */
    public static LocalDate parseDate(String dateStr, DateFormat format) {
        return LocalDate.parse(dateStr, format.formatter);
    }

    /**
     * String 转 LocalDateTime
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime parseDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * String 转 LocalDateTime
     *
     * @param dateStr
     * @param format  时间格式
     * @return
     */
    public static LocalDateTime parseDateTime(String dateStr, DateFormat format) {
        return LocalDateTime.parse(dateStr, format.formatter);
    }

    /**
     * LocalDateTime 转 String
     *
     * @param dateTime
     * @return
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return DEFAULT_DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * LocalDateTime 转 String
     *
     * @param dateTime
     * @param format 时间格式
     * @return
     */
    public static String formatDateTime(LocalDateTime dateTime, DateFormat format) {
        return format.formatter.format(dateTime);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String now() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     *
     * @param format 时间格式
     * @return
     */
    public static String now(DateFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }

    /**
     * Date转LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convert(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转Date
     * @param dateTime
     * @return
     */
    public static Date convert(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
