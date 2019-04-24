package com.system.attendance.service;

import com.system.attendance.model.MeetingRoomInfo;
import com.system.attendance.model.MeetingRoomUse;

import java.util.HashMap;
import java.util.List;

public interface IMeetingRoomService {

    List<MeetingRoomInfo> getAllRoom();
    List<MeetingRoomUse> getNoApplyRoom();
    List<MeetingRoomUse> getRoomUse();
    int agreeRoomApply(String useRoomId);
    int disAgreeRoomApply(String useRoomId);
    int addOneRoom(MeetingRoomInfo meetInfo);
    int deleteRoom(String roomId);
    List<MeetingRoomUse> queryByLike(HashMap<String,Object> map);
}
