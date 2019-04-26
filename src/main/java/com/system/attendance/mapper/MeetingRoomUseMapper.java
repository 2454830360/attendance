package com.system.attendance.mapper;

import com.system.attendance.model.MeetingRoomUse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MeetingRoomUseMapper {

    //通过user_id查会议室使用记录
    List<MeetingRoomUse> userUseRoom(@Param("userId") String userId,@Param("time") String time);

    //通过room_id查会议室使用记录
    List<MeetingRoomUse> roomUserById(@Param("roomId") String roomId,@Param("time") String time);

    //模糊查询
    List<MeetingRoomUse> selectByLike(HashMap<String,Object> map);

    //同意申请
    int agreeApply(String useRoomId);

    //不同意申请
    int disAgreeApply(String useRoomId);

    //未审批会议室审批
    List<MeetingRoomUse> getNoApply();

    //已审批过会议室使用记录
    List<MeetingRoomUse> getApply();

    int deleteByPrimaryKey(String useRoomId);

    int insert(MeetingRoomUse record);

    int insertSelective(MeetingRoomUse record);

    MeetingRoomUse selectByPrimaryKey(String useRoomId);

    int updateByPrimaryKeySelective(MeetingRoomUse record);

    int updateByPrimaryKey(MeetingRoomUse record);
}