package com.system.attendance.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UUIDUtil {

    private static final Logger LOG = LoggerFactory.getLogger(UUIDUtil.class);

    public static void main(String[] args){
        System.out.println("用户id："+createUserId());
        System.out.println("考勤id："+createAttendanceId());
        System.out.println("请假id："+createLeaveWorkId());
        System.out.println("出差id："+createOutWorkId());
    }

    /**
     * 生成用户id：U+8位
     * @return
     */
    public static String createUserId(){
        StringBuilder sb = new StringBuilder("U");
        String userId = sb.append(getUUId()).toString();
        return userId;
    }
    /**
     * 生成考勤唯一id ：A+时间+8位随机数
     * @return
     */
    public static String createAttendanceId(){
        StringBuilder sb = new StringBuilder("A");
        String attId = sb.append(getTime()).append(getUUId()).toString();
        return attId;
    }

    /**
     * 请假id：L+时间+8位唯一数
     * @return
     */
    public static String createLeaveWorkId(){
        StringBuilder sb = new StringBuilder("Leave");
        String leaveId = sb.append(getTime()).append(getUUId()).toString();
        return leaveId;
    }
    /**
     * 请假id：L+时间+8位唯一数
     * @return
     */
    public static String createOutWorkId(){
        StringBuilder sb = new StringBuilder("Out");
        String leaveId = sb.append(getTime()).append(getUUId()).toString();
        return leaveId;
    }
    /**
     * 会议室使用id：RU+时间+8位唯一数
     * @return
     */
    public static String createRoomUseId(){
        StringBuilder sb = new StringBuilder("RU");
        String leaveId = sb.append(getTime()).append(getUUId()).toString();
        return leaveId;
    }


    /**
     * 生成8位随机数
     * @return
     */
    public static String getUUId(){
        String ipAddress = "";
        try {
            //获取服务器ip地址
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            LOG.error("getNewUserId="+e.getMessage());
        }
        //获取UUID
        String uuids = ipAddress + "$" + UUID.randomUUID().toString().replace("-","").toUpperCase();
        //生成8位
        long suffix = Math.abs(uuids.hashCode()%100000000);
        String uuid = String.valueOf(suffix);
        return uuid;
    }

    /**
     * 获取时间
     * 使用JDK8新增的线程安全的时间格式化类
     * @return
     */
    public static String getTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return dateTimeFormatter.format(localDateTime);
    }

}
