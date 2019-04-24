package com.system.attendance.model;


public class OutWork {
    private String outWorkId;

    private String userId;

    private String userName;

    private String dept;

    private String outBeginTime;

    private String outEndTime;

    private String outDay;

    private String outReason;

    private String outStatus;

    private String adminId;

    @Override
    public String toString() {
        return "OutWork{" +
                "outWorkId='" + outWorkId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", dept='" + dept + '\'' +
                ", outBeginTime='" + outBeginTime + '\'' +
                ", outEndTime='" + outEndTime + '\'' +
                ", outDay='" + outDay + '\'' +
                ", outReason='" + outReason + '\'' +
                ", outStatus='" + outStatus + '\'' +
                ", adminId='" + adminId + '\'' +
                '}';
    }

    public String getOutWorkId() {
        return outWorkId;
    }

    public void setOutWorkId(String outWorkId) {
        this.outWorkId = outWorkId == null ? null : outWorkId.trim();
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

    public String getOutBeginTime() {
        return outBeginTime;
    }

    public void setOutBeginTime(String outBeginTime) {
        this.outBeginTime = outBeginTime;
    }

    public String getOutEndTime() {
        return outEndTime;
    }

    public void setOutEndTime(String outEndTime) {
        this.outEndTime = outEndTime;
    }

    public String getOutDay() {
        return outDay;
    }

    public void setOutDay(String outDay) {
        this.outDay = outDay == null ? null : outDay.trim();
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason == null ? null : outReason.trim();
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus == null ? null : outStatus.trim();
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

}