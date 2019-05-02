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
    int deleteErrRight(String userId,String time);
    int updateStatus(String attendId);
    List<Attendance> queryByLike(HashMap<String,Object> map);
    List<Attendance> userQueryByLike(HashMap<String,Object> map);
    List<Attendance> getAttendById(String userId);
    int countAttendById(String attendId);
    int userSayWhyErr(String attendId,String remarks);
    int userSignStatus(String userId,String time);
    int userSignStatusErr(String userId,String time);
    int userSignIn(Attendance record);
    int userSignInERR(Attendance record);
    int userSignOut(String userId,String time,String signOutTime);
    int userSignOutErr(String userId,String time,String signOutTime);
    Attendance userYesToNo(String userId,String time);
    AttendanceErr userNoToYes(String userId, String time);
    List<Attendance> getAttendByDept(String dept,String time);

}
