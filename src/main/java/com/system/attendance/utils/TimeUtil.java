package com.system.attendance.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static void main(String[] args){
        System.out.println(todayStringTime());
    }

    /**
     * 获取系统当日时间   格式：2019-04-13
     * @return
     */
    public static String todayStringTimeToDB(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return sdf.format(now);
    }
    public static String todayStringTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        return sdf.format(now);
    }

    /**
     * 创建时间
     * @return
     */
    public static String createTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }

    /**
     * 格式化考勤时间  格式：2019-04-13 22:06:10
     * @param time
     * @return
     */
    public static String changeDBDateTimeFormat(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(time);
    }

    /**
     * 格式化上班日期  格式：2019-04-13
     * @param time
     * @return
     */
    public static String changeDBTimeFormat(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

}
