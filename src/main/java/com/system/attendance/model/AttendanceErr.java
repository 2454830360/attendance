package com.system.attendance.model;


public class AttendanceErr {
    private String attendanceId;

    private String userId;

    private String userName;

    private String dept;

    private String signInTime;

    private String signOutTime;

    private String attendanceStatus;

    private String attendanceType;

    private String isOvertime;

    private Double overtimeTime;

    private String attendanceRemarks;

    private String time;

    @Override
    public String toString() {
        return "AttendanceErr{" +
                "attendanceId='" + attendanceId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", dept='" + dept + '\'' +
                ", signInTime='" + signInTime + '\'' +
                ", signOutTime='" + signOutTime + '\'' +
                ", attendanceStatus='" + attendanceStatus + '\'' +
                ", attendanceType='" + attendanceType + '\'' +
                ", isOvertime='" + isOvertime + '\'' +
                ", overtimeTime=" + overtimeTime +
                ", attendanceRemarks='" + attendanceRemarks + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId == null ? null : attendanceId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus == null ? null : attendanceStatus.trim();
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType == null ? null : attendanceType.trim();
    }

    public String getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(String isOvertime) {
        this.isOvertime = isOvertime == null ? null : isOvertime.trim();
    }

    public Double getOvertimeTime() {
        return overtimeTime;
    }

    public void setOvertimeTime(Double overtimeTime) {
        this.overtimeTime = overtimeTime;
    }

    public String getAttendanceRemarks() {
        return attendanceRemarks;
    }

    public void setAttendanceRemarks(String attendanceRemarks) {
        this.attendanceRemarks = attendanceRemarks == null ? null : attendanceRemarks.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}