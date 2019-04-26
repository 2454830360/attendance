package com.system.attendance.service.impl;

import com.system.attendance.mapper.MeetingRoomInfoMapper;
import com.system.attendance.mapper.MeetingRoomUseMapper;
import com.system.attendance.model.MeetingRoomInfo;
import com.system.attendance.model.MeetingRoomUse;
import com.system.attendance.service.IMeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MeetRoomService implements IMeetingRoomService {

    @Autowired
    private MeetingRoomInfoMapper meetingRoomInfoMapper;
    @Autowired
    private MeetingRoomUseMapper meetingRoomUseMapper;


    /**
     * 获取所有会议室信息
     * @return
     */
    @Override
    public List<MeetingRoomInfo> getAllRoom() {
        return meetingRoomInfoMapper.selectAll();
    }

    //新增会议室
    @Override
    public int addOneRoom(MeetingRoomInfo meetInfo) {
        return meetingRoomInfoMapper.insertSelective(meetInfo);
    }

    //删除会议室
    @Override
    public int deleteRoom(String roomId) {
        return meetingRoomInfoMapper.deleteByPrimaryKey(roomId);
    }

    //获取未审批会议室审批
    @Override
    public List<MeetingRoomUse> getNoApplyRoom() {
        return meetingRoomUseMapper.getNoApply();
    }

    @Override
    public List<MeetingRoomUse> getRoomUse() {
        return meetingRoomUseMapper.getApply();
    }

    //管理员同意会议室申请
    @Override
    public int agreeRoomApply(String useRoomId) {
        return meetingRoomUseMapper.agreeApply(useRoomId);
    }

    //管理员不同意会议室申请
    @Override
    public int disAgreeRoomApply(String useRoomId) {
        return meetingRoomUseMapper.disAgreeApply(useRoomId);
    }

    //模糊查询
    @Override
    public List<MeetingRoomUse> queryByLike(HashMap<String, Object> map) {
        return meetingRoomUseMapper.selectByLike(map);
    }

    //检查是否有该会议室
    @Override
    public int checkRoomId(String roomId) {
        return meetingRoomInfoMapper.checkRoomById(roomId);
    }
    //新增会议室使用记录
    @Override
    public int addRoomUse(MeetingRoomUse meetingRoomUse) {
        return meetingRoomUseMapper.insertSelective(meetingRoomUse);
    }

    //用户查询自己申请的会议室情况
    @Override
    public List<MeetingRoomUse> userUseRoom(String userId, String time) {
        return meetingRoomUseMapper.userUseRoom(userId,time);
    }

    //查询会议室使用情况
    @Override
    public List<MeetingRoomUse> queryRoomUserByRoomId(String roomId, String time) {
        return meetingRoomUseMapper.roomUserById(roomId,time);
    }
}
