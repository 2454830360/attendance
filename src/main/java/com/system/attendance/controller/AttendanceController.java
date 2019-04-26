package com.system.attendance.controller;

import com.system.attendance.model.Attendance;
import com.system.attendance.model.User;
import com.system.attendance.service.impl.AttendanceService;
import com.system.attendance.service.impl.UserService;
import com.system.attendance.utils.JWTUtil;
import com.system.attendance.utils.TimeUtil;
import com.system.attendance.utils.UUIDUtil;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/attend/*")
public class AttendanceController {

    private static final Logger LOG = LoggerFactory.getLogger(AttendanceController.class);


    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserService userService;

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
        LOG.info("管理员查询考勤信息----"+userName+"-"+dept+"-"+beginTime+"-"+endTime);
        List<Attendance> attendances = attendanceService.queryByLike(maps);

        return attendances;
    }

    //用户考勤接口
    @RequestMapping("sign")
    public String userSignIn(@RequestBody JSONObject json) throws ServletException {
        Attendance attendance = new Attendance();
        String status;
        String userId = null;
        String token = null;
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(json.has("token")&&!(("").equals(json.getString("token")))){
            token = json.getString("token");
            //检查token
            JWTUtil.checkToken(token);
        }
        String userName;
        String dept;
        //通过userId获取部门和姓名
        User users = userService.getOneUserById(userId);
        userName = users.getUserName();
        dept = users.getDept();
        String time = TimeUtil.todayStringTimeToDB();//签到日期

        int yes = attendanceService.userSignStatus(userId, time);
        int not = attendanceService.userSignStatusErr(userId, time);


        if(userId != null){
            //判断用户今日是否进行考勤
            if(yes == 0 && not == 0){
                //用户签到
                String attendanceId = UUIDUtil.createAttendanceId();
                String signInTime = TimeUtil.userSignTime();
                String attendanceStatus;
                String attendanceType;
                if(TimeUtil.getWeek() >=1 && TimeUtil.getWeek() <=5){
                    //工作日考勤
                    attendanceType = "工作日考勤";
                }else{
                    //周末考勤
                    attendanceType = "周末考勤";
                }
                attendance.setAttendanceId(attendanceId);
                attendance.setUserId(userId);
                attendance.setUserName(userName);
                attendance.setDept(dept);
                attendance.setSignInTime(signInTime);
                attendance.setAttendanceType(attendanceType);
                attendance.setTime(time);

                if(TimeUtil.checkSignInStatus(signInTime)){
                    //正常签到
                    attendanceStatus = "1";
                    attendance.setAttendanceStatus(attendanceStatus);
                    int j = attendanceService.userSignIn(attendance);
                    if(j == 1){
                        LOG.info(userName+"----用户签到成功----"+signInTime);
                        status = "in_true";
                    }else{
                        LOG.info(userName+"----用户签到失败，请重新签到----");
                        status = "in_false";
                    }
                }else{
                    //异常签到
                    attendanceStatus = "";
                    attendance.setAttendanceStatus(attendanceStatus);
                    int j = attendanceService.userSignInERR(attendance);
                    if(j == 1){
                        LOG.info(userName+"----用户签到成功,但是迟到了----"+signInTime);
                        status = "in_true";
                    }else{
                        LOG.info(userName+"----用户签到失败，请重新签到----");
                        status = "in_false";
                    }
                }
            }else{
                //用户已签到，此处进行签退
                String signOutTime;
                signOutTime = TimeUtil.userSignTime();
                if(TimeUtil.checkSignOutStatus(signOutTime) && yes == 1){
                    //正常签退
                    int k = attendanceService.userSignOut(userId, time, signOutTime);
                    if(k == 1){
                        LOG.info(userName+"----用户签退成功----"+signOutTime);
                        status =  "out_true";
                    }else{
                        LOG.info(userName+"----用户签退失败，请重新签退----");
                        status = "out_false";
                    }
                }else{
                    //签到为迟到或早退
                    int k;
                    if(yes == 1){
                        //正常签到的情况早退
                        Attendance attendances = attendanceService.userYesToNo(userId, time);
                        attendances.setAttendanceStatus(null);
                        attendances.setSignOutTime(signOutTime);
                        k = attendanceService.userSignInERR(attendances);
                        attendanceService.deleteErrRight(userId, time);
                    }else{
                        //异常签到的情况签退
                        k = attendanceService.userSignOutErr(userId, time, signOutTime);
                    }
                    if(k == 1){
                        status =  "out_true";
                        LOG.info(userName+"----用户签退成功----"+signOutTime);
                    }else{
                        status = "out_false";
                        LOG.info(userName+"----用户签退失败，请重新签退----");
                    }
                }
            }
        }else{
            LOG.info("userId为空----"+userId);
            return "id_null";
        }
        return status;
    }

    //用户查询自己的考勤记录
    @RequestMapping("getById")
    public List<Attendance> getAttendById(@RequestBody(required = false) JSONObject json){
        String userId = null;
        List<Attendance> attendById;
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(userId != null){
            attendById = attendanceService.getAttendById(userId);
            LOG.info("用户查询自己的考勤记录，user_id----"+userId);
            return attendById;
        }else{
            LOG.info("user_id为空，user_id----"+userId);
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
        LOG.info("用户查询考勤信息----"+userId+"-"+beginTime+"-"+endTime);

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
                    LOG.info("用户提交异常考勤原因成功----"+attendanceId+"-"+attendanceRemark);
                    result = "true";
                }
            }else{
                LOG.info("考勤id不存在！attendanceId----"+attendanceId);
            }
        }else{
            LOG.info("attendanceId和attendanceRemark有问题----"+attendanceRemark+"-"+attendanceRemark);
        }
        return result;
    }

    //查看团队成员到岗情况
    @RequestMapping("team")
    public List<Attendance> queryTeamAttend(@RequestBody JSONObject json){
        String userId = null;
        String dept;
        String time = TimeUtil.todayStringTime();
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(userId!=null){
            User users = userService.getOneUserById(userId);
            dept = users.getDept();
            List<Attendance> attendByDept = attendanceService.getAttendByDept(dept, time);
            return attendByDept;
        }else{
            LOG.info("user_id为空");
            return null;
        }
    }


}
