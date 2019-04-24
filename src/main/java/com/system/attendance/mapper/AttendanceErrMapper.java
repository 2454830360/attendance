package com.system.attendance.mapper;

import com.system.attendance.model.AttendanceErr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AttendanceErrMapper {

    int selectErrCount();

    //获取所有异常考勤信息
    List<AttendanceErr> selectAll();

    int deleteByPrimaryKey(String attendanceId);

    int insert(AttendanceErr record);

    int insertSelective(AttendanceErr record);

    AttendanceErr selectByPrimaryKey(String attendanceId);

    int updateByPrimaryKeySelective(AttendanceErr record);

    int updateByPrimaryKey(AttendanceErr record);
}