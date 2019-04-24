package com.system.attendance.controller;

import com.system.attendance.model.MeetingRoomInfo;
import com.system.attendance.model.MeetingRoomUse;
import com.system.attendance.service.impl.MeetRoomService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/room/*")
public class MeetRoomController {

    private static final Logger LOG = LoggerFactory.getLogger(MeetRoomController.class);

    @Autowired
    private MeetRoomService meetRoomService;

    /**
     * 获取所有会议室信息
     * @return
     */
    @RequestMapping("getAll")
    public List<MeetingRoomInfo> getAllRoom(){
        return meetRoomService.getAllRoom();
    }

    @RequestMapping("addOne")
    public List<MeetingRoomInfo> addRoom(@RequestBody JSONObject json){
        MeetingRoomInfo meetingRoomInfo = new MeetingRoomInfo();
        String roomId = null;
        String roomName = null;
        if(json.has("room_id")&&!(("").equals(json.getString("room_id")))){
            roomId = json.getString("room_id");
        }
        if(json.has("room_name")&&!(("").equals(json.getString("room_name")))){
            roomName = json.getString("room_name");
        }
        if(roomId != null && roomName != null){
            meetingRoomInfo.setRoomId(roomId);
            meetingRoomInfo.setRoomName(roomName);
            int i = meetRoomService.addOneRoom(meetingRoomInfo);
            if(i == 1){
                LOG.info("新增会议室成功-"+roomId+"-"+roomName);
                return meetRoomService.getAllRoom();
            }else{
                LOG.info("新增会议室失败-"+roomId+"-"+roomName);
            }
        }else {
            LOG.info("id and name is null!!!");
        }
        return meetRoomService.getAllRoom();
    }

    //删除会议室
    @RequestMapping("deleteOne")
    public List<MeetingRoomInfo> deleteRoom(@RequestBody JSONObject json){
        String roomId = null;
        if(json.has("room_id")&&!(("").equals(json.getString("room_id")))){
            roomId = json.getString("room_id");
        }
        if(roomId != null){
            int i = meetRoomService.deleteRoom(roomId);
            if(i == 1){
                LOG.info("删除会议室成功！roomId:"+roomId);
                return meetRoomService.getAllRoom();
            }else {
                LOG.info("删除会议室失败！roomId:"+roomId);
            }
        }else{
            LOG.info("删除会议室值失败！roomId:"+roomId);
        }
        return meetRoomService.getAllRoom();
    }

    //获取会议室使用记录
    @RequestMapping("getRoomUse")
    public List<MeetingRoomUse> getAllRoomUse(){
        return meetRoomService.getRoomUse();
    }

    //通过使用者、会议室编号、时间筛选会议室
    @RequestMapping("query")
    public List<MeetingRoomUse> queryByLike(@RequestBody JSONObject json){
        HashMap<String,Object> maps = new HashMap<String,Object>();
        String userName = null;
        String roomId = null;

        if(json.has("user_name")&&!(("").equals(json.getString("user_name")))){
            userName = json.getString("user_name");
        }
        if(json.has("room_id")&&!(("").equals(json.getString("room_id")))){
            roomId = json.getString("room_id");
        }

        maps.put("userName",userName);
        maps.put("roomId",roomId);
        LOG.info("查询会议室信息："+userName+"-"+roomId);

        List<MeetingRoomUse> meetingRoomUses = meetRoomService.queryByLike(maps);

        return meetingRoomUses;
    }


}
