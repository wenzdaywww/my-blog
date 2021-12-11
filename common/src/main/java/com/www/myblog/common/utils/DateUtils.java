package com.www.myblog.common.utils;

import org.apache.commons.lang.StringUtils;
import sun.util.calendar.LocalGregorianCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>@Description 日期处理工具 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/4 15:07 </p>
 */
public class DateUtils {
    /**
     * <p>@Description 获取当前系统日期时间 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:10 </p>
     * @return java.util.Date
     */
    public static Date getCurrentDateTime(){
        return new Date();
    }
    /**
     * <p>@Description 日期转换字符串 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:11 </p>
     * @param date
     * @param dateFormat
     * @return java.lang.String
     */
    public static String format(Date date,DateFormatEnum dateFormat){
        if(date == null || dateFormat == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.format);
        return sdf.format(date);
    }

    public static void main(String[] args) {
        DateUtils.parse("2021-11-29",DateFormatEnum.YYYY_MM_DD);
    }
    /**
     * <p>@Description 字符串转为日期 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/7 22:59 </p>
     * @param dateStr 字符串日期
     * @param dateFormat 日期格式
     * @return java.util.Date 日期
     */
    public static Date parse(String dateStr,DateFormatEnum dateFormat){
        if(StringUtils.isBlank(dateStr) || dateFormat == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    /**
     * <p>@Description 日期格式 </p>
     * <p>@Version 1.0 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:22 </p>
     */
    public enum DateFormatEnum{
        YYYYMMDD("yyyyMMdd"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
        YYYYMMDDHHMMSSSSS("yyyyMMddHHmmssSSS"),
        ;
        /** 格式 **/
        private String format;

        DateFormatEnum(String format){
            this.format = format;
        }
    }
}
