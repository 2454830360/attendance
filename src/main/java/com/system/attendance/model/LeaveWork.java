package com.system.attendance.model;


public class LeaveWork {
    private String leaveWorkId;

    private String userId;

    private String userName;

    private String dept;

    private String leaveBeginTime;

    private String leaveEndTime;

    private String leaveDay;

    private String leaveReason;

    private String leaveStatus;

    private String adminId;

    private String time;

    @Override
    public String toString() {
        return "LeaveWork{" +
                "leaveWorkId='" + leaveWorkId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", dept='" + dept + '\'' +
                ", leaveBeginTime='" + leaveBeginTime + '\'' +
                ", leaveEndTime='" + leaveEndTime + '\'' +
                ", leaveDay='" + leaveDay + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", leaveStatus='" + leaveStatus + '\'' +
                ", adminId='" + adminId + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getLeaveWorkId() {
        return leaveWorkId;
    }

    public void setLeaveWorkId(String leaveWorkId) {
        this.leaveWorkId = leaveWorkId == null ? null : leaveWorkId.trim();
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

    public String getLeaveBeginTime() {
        return leaveBeginTime;
    }

    public void setLeaveBeginTime(String leaveBeginTime) {
        this.leaveBeginTime = leaveBeginTime;
    }

    public String getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(String leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public String getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        this.leaveDay = leaveDay == null ? null : leaveDay.trim();
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason == null ? null : leaveReason.trim();
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus == null ? null : leaveStatus.trim();
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}