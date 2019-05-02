package com.system.attendance.controller;

import com.system.attendance.model.LeaveWork;
import com.system.attendance.model.User;
import com.system.attendance.service.impl.LeaveWorkService;
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
@RequestMapping("/leave/*")
public class LeaveWorkController {

    private static final Logger LOG = LoggerFactory.getLogger(LeaveWorkController.class);

    @Autowired
    private LeaveWorkService leaveWorkService;
    @Autowired
    private UserService userService;

    /**
     * 获取所有请假信息
     * @return
     */
    @RequestMapping("getAll")
    public List<LeaveWork> getAllLeave(){
        return leaveWorkService.getAllLeave();
    }

    //通过姓名、部门、请假时间（区间）模糊查询请假记录
    @RequestMapping("query")
    public List<LeaveWork> queryLeaveByLike(@RequestBody JSONObject json){
        HashMap<String,Object> maps = new HashMap<String,Object>();
        String userName = null;
        String dept = null;
        String beginTime = null;
        String endTime = null;

        if(json.has("user_name")&&!(("").equals(json.getString("user_name")))){
            userName = json.getString("user_name");
        }
        if(json.has("dept")&&!(("").equals(json.getString("dept")))){
            dept = json.getString("dept");
        }
        if(json.has("beginTime")&&!(("").equals(json.getString("beginTime")))){
            beginTime = json.getString("beginTime");
        }
        if(json.has("endTime")&&!(("").equals(json.getString("endTime")))){
            endTime = json.getString("endTime");
        }
        maps.put("userName",userName);
        maps.put("dept",dept);
        maps.put("beginTime",beginTime);
        maps.put("endTime",endTime);
        LOG.info("管理员查询请假信息:"+userName+"-"+dept+"-"+beginTime+"-"+endTime);

        List<LeaveWork> leaveWorks = leaveWorkService.queryByLike(maps);
        return leaveWorks;
    }

    //用户请假申请
    @RequestMapping("userApply")
    public String userLeaveApply(@RequestBody JSONObject json){
        String result = "false";
        LeaveWork leaveWork = new LeaveWork();
        String leaveWorkId = UUIDUtil.createLeaveWorkId();
        String userId = null;
        String userName = null;
        String dept = null;
        String leaveBeginTime = null;
        String leaveEndTime = null;
        String leaveDay = null;
        String leaveReason = null;
        String time = TimeUtil.todayStringTimeToDB();

        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(json.has("leave_begin_time")&&!(("").equals(json.getString("leave_begin_time")))){
            leaveBeginTime = json.getString("leave_begin_time");
        }
        if(json.has("leave_end_time")&&!(("").equals(json.getString("leave_end_time")))){
            leaveEndTime = json.getString("leave_end_time");
        }
        if(json.has("leave_reason")&&!(("").equals(json.getString("leave_reason")))){
            leaveReason = json.getString("leave_reason");
        }
        if(userId!=null){
            User users = userService.getOneUserById(userId);
            userName = users.getUserName();
            dept = users.getDept();
        }else{
            LOG.info("user_id为空");
        }

        leaveWork.setLeaveWorkId(leaveWorkId);
        leaveWork.setUserId(userId);
        leaveWork.setUserName(userName);
        leaveWork.setDept(dept);
        leaveWork.setLeaveBeginTime(leaveBeginTime);
        leaveWork.setLeaveEndTime(leaveEndTime);
        leaveWork.setLeaveDay(leaveDay);
        leaveWork.setLeaveReason(leaveReason);
        leaveWork.setLeaveStatus("0");
        leaveWork.setTime(time);

        int i = leaveWorkService.addOneLeave(leaveWork);
        if(i == 1){
            LOG.info("用户提交请假申请成功！userName："+userName);
            result = "true";
        }
        return result;
    }

    //用户查询未审批的请假申请
    @RequestMapping("userNoAudit")
    public List<LeaveWork> queryNoAuditUserLeave(@RequestBody JSONObject json){
        String userId = null;
        List<LeaveWork> leaveWorks = null;
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(userId != null){
            leaveWorks = leaveWorkService.noAuditUserLeave(userId);
        }
        return leaveWorks;
    }

    //用户根据id、时间查询请假信息
    @RequestMapping("userQuery")
    public List<LeaveWork> userQueryByLike(@RequestBody JSONObject json){
        HashMap<String,Object> maps = new HashMap<String,Object>();
        String userId = null;
        String beginTime = null;
        String endTime = null;

        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(json.has("beginTime")&&!(("").equals(json.getString("beginTime")))){
            beginTime = json.getString("beginTime");
        }
        if(json.has("endTime")&&!(("").equals(json.getString("endTime")))){
            endTime = json.getString("endTime");
        }
        maps.put("userId",userId);
        maps.put("beginTime",beginTime);
        maps.put("endTime",endTime);
        LOG.info("用户查询请假信息:"+userId+"-"+beginTime+"-"+endTime);

        List<LeaveWork> leaveWorks = leaveWorkService.queryUserLeaveByLike(maps);

        return leaveWorks;
    }

}
