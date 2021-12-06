package com.www.myblog.common.utils;

import org.apache.commons.lang.StringUtils;

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
     * <p>@Description 获取当前系统日期 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:10 </p>
     * @return java.util.Date
     */
    public static Date getCurrentDate(){
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
    /**
     * <p>@Description 日期格式 </p>
     * <p>@Version 1.0 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:22 </p>
     */
    public enum DateFormatEnum{
        YYYY_MM_DD("yyyy-mm-dd"),
        YYYY_MM_DD_HH_MM_SS("yyyy-mm-dd HH:mm:ss"),
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
