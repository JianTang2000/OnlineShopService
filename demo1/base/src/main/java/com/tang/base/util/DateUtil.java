package com.tang.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * < 时间工具类 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/12 <br>
 */
public final class DateUtil {
    /**
     * < 日志 >
     */
    private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    private DateUtil() {

    }

    public static final String DATE_FORMAT_1 = "yyyy-MM-dd";

    public static final String DATE_FORMAT_2 = "yyyyMMdd";

    public static final String DATETIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_FORMAT_3 = "yyyy-MM-dd HH-mm-ss";

    public static final String DATETIME_FORMAT_2 = "yyyyMMddHHmmss";

    public static final String DATETIME_FORMAT_4 = "yyyy/MM/dd HH:mm:ss";

    public static final String DATETIME_FORMAT_5 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATETIME_FORMAT_6 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_3 = "yyyy年MM月dd日";

    public static final String DEFAULT_DATE_FORMAT = DATE_FORMAT_1;

    public static final String DEFAULT_TIME_FORMAT = DATETIME_FORMAT_1;

    public static final String NAME_FILE_DATE_FORMAT = "yyyyMMdd_HHmmss";

    /**
     * < yyyy-MM-dd >
     */
    public static String getNowTimeString() {
        Date date = new Date();
        return date2String(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * < yyyy-MM-dd >
     */
    public static Date getNowTimeDate() {
        Date date = new Date();
        String date2String = date2String(date, DEFAULT_DATE_FORMAT);
        return string2Date(date2String);
    }

    public static Date string2Date(String date) {
        Date ret = null;
        if (date == null || date.length() == 0) {
            return ret;
        }
        if (date.length() > 11) {
            if (date.indexOf('-') > 0) {
                if (date.indexOf(':') > 0) {
                    ret = string2Date(date, DATETIME_FORMAT_1);
                }
                else {
                    ret = string2Date(date, DATETIME_FORMAT_3);
                }
            }
            else if (date.indexOf('/') > 0) {
                ret = string2Date(date, DATETIME_FORMAT_4);
            }
            else {
                ret = string2Date(date, DATETIME_FORMAT_2);
            }
        }
        else {
            if (date.indexOf('-') > 0) {
                ret = string2Date(date, DATE_FORMAT_1);
            }
            else if (date.length() == 8) {
                ret = string2Date(date, DATE_FORMAT_2);
            }
            else {
                ret = string2Date(date, DATE_FORMAT_3);
            }
        }
        return ret;
    }

    public static Date string2Date(String date, String format) {
        if (!ValidateUtil.validateNotEmpty(format)) {
            throw new IllegalArgumentException("the date format string is null!");
        }
        SimpleDateFormat sdf = getDateFormat(format);
        if (!ValidateUtil.validateNotNull(sdf)) {
            throw new IllegalArgumentException("the date format string is not matching available format object");
        }
        try {
            return sdf.parse(date);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("the date string " + date + " is not matching format: " + format, e);
        }
    }

    public static String date2String(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = getDateFormat(format);
        return sdf.format(date);
    }

    public static SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

}
