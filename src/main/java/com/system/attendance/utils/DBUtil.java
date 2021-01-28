package com.system.attendance.utils;

import com.system.attendance.model.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance_system?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection conn=null;

    static {
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取数据库连接
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return conn;
    }

    /**
     * 统计数据库记录条数是否变化
     * @return
     */
    public static int getCount(){
        String now_time = TimeUtil.todayStringTime();
        String sql = "select count(*) from attendance where time = "+now_time;
        int count = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {
            if(conn != null){
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                if(rs.next()){
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 获取系统当天所有考勤信息
     * @return
     */
    public static List<Attendance> getTodayAttend(){
        List<Attendance> todayAttend = new ArrayList<Attendance>();
        String now_time = TimeUtil.todayStringTime();
//        String sql = "select * from attendance where time = "+now_time+" ORDER BY sign_in_time desc";
        String sql = "select * from attendance ORDER BY sign_in_time desc";
        ResultSet rs;
        PreparedStatement ps;
        try {
            if(conn!=null){
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while(rs.next()){
                    String attendance_id = rs.getString("attendance_id");
                    String user_id = rs.getString("user_id");
                    String user_name = rs.getString("user_name");
                    String dept = rs.getString("dept");
                    String sign_in_time = rs.getString("sign_in_time");
                    String sign_out_time = rs.getString("sign_out_time");
                    String attendance_status = rs.getString("attendance_status");
                    String attendance_type = rs.getString("attendance_type");
                    String is_overtime = rs.getString("is_overtime");
                    Double overtime_time = rs.getDouble("overtime_time");
                    String attendance_remarks = rs.getString("attendance_remarks");
                    String time = rs.getString("time");

                    Attendance attendance = new Attendance(attendance_id,user_id,user_name,dept,sign_in_time,sign_out_time,attendance_status,attendance_type,is_overtime,overtime_time,attendance_remarks,time);

                    todayAttend.add(attendance);
                }
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return todayAttend;
    }

    public static void main(String[] args) {
        List<Attendance> todayAttend = getTodayAttend();
        System.out.println(todayAttend);
    }

}
