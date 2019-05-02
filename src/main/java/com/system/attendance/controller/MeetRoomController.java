package com.system.attendance.controller;

import com.system.attendance.model.MeetingRoomInfo;
import com.system.attendance.model.MeetingRoomUse;
import com.system.attendance.model.User;
import com.system.attendance.service.impl.MeetRoomService;
import com.system.attendance.service.impl.UserService;
import com.system.attendance.utils.TimeUtil;
import com.system.attendance.utils.UUIDUtil;
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
    @Autowired
    private UserService userService;

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

    //通过使用者姓名、会议室编号查询会议室使用记录
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

    //用户申请使用会议室
    @RequestMapping("userApply")
    public String userApplyRoom(@RequestBody JSONObject json){
       MeetingRoomUse roomUse = new MeetingRoomUse();
       String useRoomId = UUIDUtil.createRoomUseId();
       String roomId = null;
       String roomStatus = "0";
       String userId = null;
       String userName;
       String roomBeginTime = null;
       String roomEndTime = null;
       String roomApplyReason = null;
       String time = TimeUtil.todayStringTimeToDB();

        if(json.has("room_id")&&!(("").equals(json.getString("room_id")))){
            roomId = json.getString("room_id");
        }
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(json.has("room_begin_time")&&!(("").equals(json.getString("room_begin_time")))){
            roomBeginTime = json.getString("room_begin_time");
        }
        if(json.has("room_end_time")&&!(("").equals(json.getString("room_end_time")))){
            roomEndTime = json.getString("room_end_time");
        }
        if(json.has("room_apply_reason")&&!(("").equals(json.getString("room_apply_reason")))){
            roomApplyReason = json.getString("room_apply_reason");
        }

        if(userId != null){
            User userById = userService.getOneUserById(userId);
            userName = userById.getUserName();
        }else {
            LOG.info("user_id为空");
            return "false";
        }
        if(roomId != null){
            int i = meetRoomService.checkRoomId(roomId);
            if(i != 1){
                return "false";
            }
        }else {
            LOG.info("roomId为空");
            return "false";
        }
        int i = meetRoomService.checkRoomUseStatus(roomId, time, roomBeginTime, roomEndTime);
        if(i == 0){
            roomUse.setUseRoomId(useRoomId);
            roomUse.setRoomId(roomId);
            roomUse.setRoomStatus(roomStatus);
            roomUse.setUserId(userId);
            roomUse.setUserName(userName);
            roomUse.setRoomBeginTime(roomBeginTime);
            roomUse.setRoomEndTime(roomEndTime);
            roomUse.setRoomApplyReason(roomApplyReason);
            roomUse.setTime(time);

            int j = meetRoomService.addRoomUse(roomUse);
            if(j != 1){
                LOG.info("会议室申请失败");
                return "false";
            }
        }else{
            LOG.info("会议室该时间段已被申请，请重新选择");
            return "already_use";
        }
        LOG.info("会议室申请成功----"+userName);
        return "true";
    }

    //通过用户id查看申请会议室情况
    @RequestMapping("userUse")
    public List<MeetingRoomUse> queryUserUseRoom(@RequestBody JSONObject json){
        String userId = null;
//        String time = TimeUtil.todayStringTimeToDB();

        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(userId != null){
            List<MeetingRoomUse> meetingRoomUses = meetRoomService.userUseRoom(userId);
            return meetingRoomUses;
        }else {
            LOG.info("user_id为空");
            return null;
        }
    }

    //用户通过会议室id查询会议室申请记录
    @RequestMapping("roomUse")
    public List<MeetingRoomUse> queryRoomIdUseStatus(@RequestBody JSONObject json){
        String roomId = null;
        String time = TimeUtil.todayStringTime();
        if(json.has("room_id")&&!(("").equals(json.getString("room_id")))){
            roomId = json.getString("room_id");
        }

        if(roomId!=null){
            List<MeetingRoomUse> meetingRoomUses = meetRoomService.queryRoomUserByRoomId(roomId, time);
            return  meetingRoomUses;
        }else{
            LOG.info("room_id为空");
            return null;
        }
    }

    //获取房间id信息
    @RequestMapping("getId")
    public List<MeetingRoomInfo> getRoomId(){
        return meetRoomService.getRoomId();
    }

}
