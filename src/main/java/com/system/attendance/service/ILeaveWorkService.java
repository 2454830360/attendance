package com.system.attendance.service;

import com.system.attendance.model.LeaveWork;

import java.util.HashMap;
import java.util.List;

public interface ILeaveWorkService {

    List<LeaveWork> getAllLeave();
    List<LeaveWork> getNoApplyLeave();
    int agreeLeaveApply(String leaveId);
    int disAgreeLeaveApply(String leaveId);
    List<LeaveWork> queryByLike(HashMap<String,Object> map);
    List<LeaveWork> queryUserLeaveByLike(HashMap<String,Object> map);
    int addOneLeave(LeaveWork leaveWork);
    List<LeaveWork> noAuditUserLeave(String userId);
}
