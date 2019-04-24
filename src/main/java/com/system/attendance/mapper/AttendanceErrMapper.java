package com.system.attendance.mapper;

import com.system.attendance.model.Attendance;
import com.system.attendance.model.AttendanceErr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AttendanceErrMapper {

    int selectErrCount();

    //获取所有异常考勤信息
    List<AttendanceErr> selectAll();

    //异常签退
    int userSignOut(String userId,String time,String signOutTime);

    //查看用户是否签到
    int userSignStatus(@Param("userId")String userId, @Param("time") String time);

    int deleteByPrimaryKey(String attendanceId);

    int insert(AttendanceErr record);

    int insertSelective(AttendanceErr record);

    int insertSelectiveRight(Attendance record);

    AttendanceErr selectByPrimaryKey(String attendanceId);

    int updateByPrimaryKeySelective(AttendanceErr record);

    int updateByPrimaryKey(AttendanceErr record);
}