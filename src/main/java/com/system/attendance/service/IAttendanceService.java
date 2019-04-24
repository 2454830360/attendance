package com.system.attendance.service;

import com.system.attendance.model.Attendance;
import com.system.attendance.model.AttendanceErr;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface IAttendanceService {

    List<Attendance> getAllAttend();
    List<AttendanceErr> getAllERRAttend();
    int selectRightCount();
    int selectErrCount();
    List<Attendance> getTodayAttend(String time);
    AttendanceErr getErrAttendById(String attendId);
    int insertErrToRight(AttendanceErr record);
    int deleteRightErr(String attendId);
    int updateStatus(String attendId);
    List<Attendance> queryByLike(HashMap<String,Object> map);
    List<Attendance> userQueryByLike(HashMap<String,Object> map);
    List<Attendance> getAttendById(String userId);
    int addOneAttend(Attendance record);
    int countAttendById(String attendId);
    int userSayWhyErr(String attendId,String remarks);

}
