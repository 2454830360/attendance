package com.system.attendance.model;


public class MeetingRoomUse {
    private String useRoomId;

    private String roomId;

    private String roomStatus;

    private String roomBeginTime;

    private String roomEndTime;

    private String userId;

    private String userName;

    private String roomApplyReason;

    private String time;

    @Override
    public String toString() {
        return "MeetingRoomUse{" +
                "useRoomId='" + useRoomId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomStatus='" + roomStatus + '\'' +
                ", roomBeginTime='" + roomBeginTime + '\'' +
                ", roomEndTime='" + roomEndTime + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", roomApplyReason='" + roomApplyReason + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUseRoomId() {
        return useRoomId;
    }

    public void setUseRoomId(String useRoomId) {
        this.useRoomId = useRoomId == null ? null : useRoomId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus == null ? null : roomStatus.trim();
    }

    public String getRoomBeginTime() {
        return roomBeginTime;
    }

    public void setRoomBeginTime(String roomBeginTime) {
        this.roomBeginTime = roomBeginTime;
    }

    public String getRoomEndTime() {
        return roomEndTime;
    }

    public void setRoomEndTime(String roomEndTime) {
        this.roomEndTime = roomEndTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoomApplyReason() {
        return roomApplyReason;
    }

    public void setRoomApplyReason(String roomApplyReason) {
        this.roomApplyReason = roomApplyReason == null ? null : roomApplyReason.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}