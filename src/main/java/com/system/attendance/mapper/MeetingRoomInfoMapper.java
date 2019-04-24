package com.system.attendance.mapper;

import com.system.attendance.model.MeetingRoomInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingRoomInfoMapper {

    List<MeetingRoomInfo> selectAll();

    int deleteByPrimaryKey(String roomId);

    int insert(MeetingRoomInfo record);

    int insertSelective(MeetingRoomInfo record);

    MeetingRoomInfo selectByPrimaryKey(String roomId);

    int updateByPrimaryKeySelective(MeetingRoomInfo record);

    int updateByPrimaryKey(MeetingRoomInfo record);
}