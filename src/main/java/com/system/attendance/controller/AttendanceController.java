package com.system.attendance.controller;

import com.system.attendance.model.Attendance;
import com.system.attendance.service.impl.AttendanceService;
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
@RequestMapping("/attend/*")
public class AttendanceController {

    private static final Logger LOG = LoggerFactory.getLogger(AttendanceController.class);


    @Autowired
    private AttendanceService attendanceService;

    //获取所有正常考勤信息
    @RequestMapping("getAll")
    public List<Attendance> getAllAttendInfo(){
        return attendanceService.getAllAttend();
    }

    //获取当日考勤信息
    @RequestMapping("getToday")
    public List<Attendance> getTodayAttend(){
        return attendanceService.getTodayAttend(TimeUtil.todayStringTime());
    }

    //通过姓名，部门，考勤日期模糊查询考勤信息
    @RequestMapping("query")
    public List<Attendance> queryAttend(@RequestBody JSONObject json){
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
        LOG.info("管理员查询考勤信息:"+userName+"-"+dept+"-"+beginTime+"-"+endTime);
        List<Attendance> attendances = attendanceService.queryByLike(maps);

        return attendances;
    }

    //用户签到接口
    @RequestMapping("signIn")
    public String userSignIn(@RequestBody JSONObject json){
        Attendance attendance = new Attendance();
        String attendanceId = UUIDUtil.createAttendanceId();
        String userId = null;
        String token = null;
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(json.has("token")&&!(("").equals(json.getString("token")))){
            token = json.getString("token");
        }
        String userName = null;
        String dept = null;
        String signInTime = TimeUtil.createTime();
        String attendanceStatus = null;
        String attendanceType = null;
        String time = TimeUtil.todayStringTimeToDB();

        attendance.setAttendanceId(attendanceId);
        attendance.setUserId(userId);
        attendance.setUserName(userName);
        attendance.setDept(dept);
        attendance.setSignInTime(signInTime);
        attendance.setAttendanceStatus(attendanceStatus);
        attendance.setAttendanceType(attendanceType);
        attendance.setTime(time);

        int i = attendanceService.addOneAttend(attendance);
        if(i == 1){
            LOG.info("用户签到成功");
            return "true";
        }

        return "false";
    }

    //用户查询自己的考勤记录
    @RequestMapping("getById")
    public List<Attendance> getAttendById(@RequestBody JSONObject json){
        String userId = null;
        List<Attendance> attendById;
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(userId != null){
            attendById = attendanceService.getAttendById(userId);
            return attendById;
        }else{
            LOG.info("user_id为空，user_id："+userId);
            return null;
        }
    }

    //用户根据时间段搜索自己的考勤记录
    @RequestMapping("userQuery")
    public List<Attendance> userQuery(@RequestBody JSONObject json){
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
        LOG.info("用户查询考勤信息:"+userId+"-"+beginTime+"-"+endTime);

        List<Attendance> uAttendances = attendanceService.userQueryByLike(maps);

        return uAttendances;
    }

    //用户提交异常考勤原因
    @RequestMapping("userERR")
    public String userSayWhyERR(@RequestBody JSONObject json){
        String result = "false";
        String attendanceId = null;
        String attendanceRemark = null;
        if(json.has("attendance_id")&&!(("").equals(json.getString("attendance_id")))){
            attendanceId = json.getString("attendance_id");
        }
        if(json.has("attendance_remarks")&&!(("").equals(json.getString("attendance_remarks")))){
            attendanceRemark = json.getString("attendance_remarks");
        }
        if(attendanceId != null && attendanceRemark != null){
            int i = attendanceService.countAttendById(attendanceId);
            if(i == 1){
                int j = attendanceService.userSayWhyErr(attendanceId, attendanceRemark);
                if(j == 1){
                    LOG.info("用户提交异常考勤原因成功，"+attendanceId+"-"+attendanceRemark);
                    result = "true";
                }
            }else{
                LOG.info("考勤id不存在！attendanceId："+attendanceId);
            }
        }else{
            LOG.info("attendanceId和attendanceRemark有问题，"+attendanceRemark+"-"+attendanceRemark);
        }

        return result;
    }


}
