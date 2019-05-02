package com.system.attendance.controller;

import com.system.attendance.model.AttendanceErr;
import com.system.attendance.model.LeaveWork;
import com.system.attendance.model.MeetingRoomUse;
import com.system.attendance.service.impl.AttendanceService;
import com.system.attendance.service.impl.LeaveWorkService;
import com.system.attendance.service.impl.MeetRoomService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 待办接口
 */
@RestController
@RequestMapping("/todo/*")
public class ToDoController {

    private static final Logger LOG = LoggerFactory.getLogger(ToDoController.class);

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private LeaveWorkService leaveWorkService;
    @Autowired
    private MeetRoomService meetRoomService;


    //异常考勤信息（未审批）
    @RequestMapping("errAttend")
    public List<AttendanceErr> getErrAttend(){
        return attendanceService.getAllERRAttend();
    }
    //未审批异常考勤数量
    @RequestMapping("countErr")
    public int countErrAttend(){
        return attendanceService.selectErrCount();
    }

    //管理员审批异常考勤信息，传入attendanceId[attendance_status]
    @RequestMapping("auditErrAttend")
    public List<AttendanceErr> auditErrAttend(@RequestBody JSONObject json){
        String attendanceId = null;
        String attendance_status = null;
        if(json.has("attendance_id")&&!(("").equals(json.getString("attendance_id")))){
            attendanceId = json.getString("attendance_id");
        }
        if(json.has("status")&&!(("").equals(json.getString("status")))){
            attendance_status = json.getString("status");
        }

        if (attendanceId != null){
            //通过id获取异常表一条信息，然后插入正常表，然后删除异常表信息
            AttendanceErr errAttendById = attendanceService.getErrAttendById(attendanceId);
            int i = attendanceService.insertErrToRight(errAttendById);
            if (i == 1){
                LOG.info("异常表进入正常表成功");
                if (attendance_status != null){
                    int k = attendanceService.updateStatus(attendanceId);
                    if (k == 1){
                        LOG.info("管理员审批为同意，status为1");
                    }
                }
                int j = attendanceService.deleteRightErr(attendanceId);
                if (j == 1){
                    LOG.info("异常表记录删除成功："+attendanceId);
                    return attendanceService.getAllERRAttend();
                }else {
                    LOG.info("异常表记录删除失败："+attendanceId);
                }
            }else {
                LOG.info("异常表进入正常表失败");
            }
        }
        return attendanceService.getAllERRAttend();
    }

    //未审批请假信息
    @RequestMapping("noAuditLeave")
    public List<LeaveWork> getLeaveWork(){
        return leaveWorkService.getNoApplyLeave();
    }

    //管理员审批请假申请
    @RequestMapping("auditLeave")
    public List<LeaveWork> auditLeaveWork(@RequestBody JSONObject json){
        String leaveWorkId = null;
        String leaveStatus = null;
        if(json.has("leave_work_id") && !(("").equals(json.getString("leave_work_id")))){
            leaveWorkId = json.getString("leave_work_id");
        }
        if(json.has("status") && !(("").equals(json.getString("status")))){
            leaveStatus = json.getString("status");
        }
        if(leaveWorkId != null){
            if(("1").equals(leaveStatus)){
                //status为1即为审批通过
                int i = leaveWorkService.agreeLeaveApply(leaveWorkId);
                if(i == 1){
                    LOG.info("管理同意请假申请！leaveWorkId："+leaveWorkId);
                    return leaveWorkService.getNoApplyLeave();
                }else{
                    LOG.info("管理员审批请假申请失败！leaveWorkId："+leaveWorkId);
                }
            }else{
                int j = leaveWorkService.disAgreeLeaveApply(leaveWorkId);
                if(j == 1){
                    LOG.info("管理不同意请假申请！leaveWorkId："+leaveWorkId);
                    return leaveWorkService.getNoApplyLeave();
                }else{
                    LOG.info("管理员审批请假申请失败！leaveWorkId："+leaveWorkId);
                }
            }
        }else{
            LOG.info("leave_work_id有问题！"+leaveWorkId);
        }
        return leaveWorkService.getNoApplyLeave();
    }

    //获取未审批会议室申请
    @RequestMapping("noAuditRoom")
    public List<MeetingRoomUse> getMeetingRoom(){
        return meetRoomService.getNoApplyRoom();
    }

    //管理员审批会议室申请
    @RequestMapping("auditRoom")
    public List<MeetingRoomUse> auditRoomApply(@RequestBody JSONObject json){
        String useRoomId = null;
        String roomStatus = null;

        if(json.has("use_room_id") && !(("").equals(json.getString("use_room_id")))){
            useRoomId = json.getString("use_room_id");
        }
        if(json.has("status") && !(("").equals(json.getString("status")))){
            roomStatus = json.getString("status");
        }
        if(useRoomId != null){
            if(("1".equals(roomStatus))){
                //status为1即为审批通过
                int i = meetRoomService.agreeRoomApply(useRoomId);
                if(i == 1){
                    LOG.info("管理员同意会议室申请！useRoomId："+useRoomId);
                    return meetRoomService.getNoApplyRoom();
                }else{
                    LOG.info("管理员审批会议室申请失败！useRoomId："+useRoomId);
                }
            }else{
                int j = meetRoomService.disAgreeRoomApply(useRoomId);
                if(j == 1){
                    LOG.info("管理员不同意会议室申请！useRoomId："+useRoomId);
                    return meetRoomService.getNoApplyRoom();
                }
            }
        }else{
            LOG.info("useRoomId有问题！"+useRoomId);
        }
        return meetRoomService.getNoApplyRoom();
    }


}
