package com.system.attendance.mapper;

import com.system.attendance.model.Attendance;
import com.system.attendance.model.AttendanceErr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    int userEarlyOut(String userId,String time);

    //签退
    int userSignOut(String userId,String time,String signOutTime);

    //查询用户是否签到
    int userSignStatus(@Param("userId")String userId,@Param("time") String time);

    List<Attendance> selectByUserId(String userId);

    //通过姓名、部门，考勤时间模糊查询考勤信息
    List<Attendance> selectByLike(HashMap<String,Object> map);

    List<Attendance> userSelectByLike(HashMap<String,Object> map);

    //统计系统当天考勤信息
    List<Attendance> getToday(String time);

    //统计记录条数
    int selectCount();

    //统计id是否存在
    int countById(String attendanceId);

    //查询全部信息
    List<Attendance> selectAll();

    int deleteByPrimaryKey(String attendanceId);

    int deleteByUserIdTime(@Param("userId") String userId,@Param("time") String time);

    int insert(Attendance record);

    int insertSelective(Attendance record);

    //将审批通过的异常考勤信息插入正常表
    int insertErrSelective(AttendanceErr record);

    Attendance selectByPrimaryKey(String attendanceId);

    //通过用户id和时间查
    Attendance selectByUserIdTime(@Param("userId") String userId,@Param("time") String time);

    int updateByPrimaryKeySelective(Attendance record);

    int updateByPrimaryKey(Attendance record);

    int updateStatus(String attendanceId);

    int updateRemark(String attendanceId,String attendanceRemarks);
}