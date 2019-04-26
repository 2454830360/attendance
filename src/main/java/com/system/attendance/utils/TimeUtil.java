package com.system.attendance.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static void main(String[] args){
        System.out.println(todayStringTime());
        System.out.println(userSignTime());
        System.out.println(createTime());
        System.out.println(getWeek());
        System.out.println(checkSignInStatus("00:59:59"));
        System.out.println(checkSignOutStatus("17:59:59"));


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
     * 用户创建时间
     * @return
     */
    public static String createTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }

    //格式化考勤时间  格式：22:06:10
    public static String userSignTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }
    //星期几
    public static Integer getWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("F");
        Date now = new Date();
        return Integer.valueOf(sdf.format(now))-1;
    }
    //判断签到时间是否正常
    public static boolean checkSignInStatus(String signInTime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date beginTime = null;
        Date endTime = null;
        Date now = null;
        try {
            //签到时间
            beginTime = sdf.parse("00:00:00");
            endTime = sdf.parse("23:00:00");
            now = sdf.parse(signInTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(now == beginTime || now == endTime){
            return true;
        }

        Calendar nowS = Calendar.getInstance();
        nowS.setTime(now);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        // A.after(B)在A>B时,返回true;在A<=B时,返回false;
        // A.before(B)在A<B时,返回true;在A>=B时,返回false;
        if(nowS.after(begin) && nowS.before(end)){
            return true;
        }else {
            return false;
        }
    }
    //判断签退时间是否正常
    public static boolean checkSignOutStatus(String signOutTime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date beginTime = null;
        Date endTime = null;
        Date now = null;
        try {
            //签退时间
            beginTime = sdf.parse("18:00:00");
            endTime = sdf.parse("23:59:59");
            now = sdf.parse(signOutTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(now == beginTime || now == endTime){
            return true;
        }


        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        Calendar nowS = Calendar.getInstance();
        nowS.setTime(now);

        if(nowS.after(begin) && nowS.before(end)){
            return true;
        }else {
            return false;
        }
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
