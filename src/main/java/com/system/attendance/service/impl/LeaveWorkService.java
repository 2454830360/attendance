package com.system.attendance.service.impl;

import com.system.attendance.mapper.LeaveWorkMapper;
import com.system.attendance.model.LeaveWork;
import com.system.attendance.service.ILeaveWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LeaveWorkService implements ILeaveWorkService {

    @Autowired
    private LeaveWorkMapper leaveWorkMapper;


    /**
     * 获取所有请假信息
     * @return
     */
    @Override
    public List<LeaveWork> getAllLeave() {
        return leaveWorkMapper.selectAll();
    }

    //获取未审批请假
    @Override
    public List<LeaveWork> getNoApplyLeave() {
        return leaveWorkMapper.selectNoApply();
    }

    //管理员同意请假申请
    @Override
    public int agreeLeaveApply(String leaveId) {
        return leaveWorkMapper.updateStatusToYes(leaveId);
    }

    //管理员不同意请假申请
    @Override
    public int disAgreeLeaveApply(String leaveId) {
        return leaveWorkMapper.updateStatusToNo(leaveId);
    }

    //通过姓名、部门、时间模糊查询请假信息
    @Override
    public List<LeaveWork> queryByLike(HashMap<String, Object> map) {
        return leaveWorkMapper.selectByLike(map);
    }

    //用户提交请假申请
    @Override
    public int addOneLeave(LeaveWork leaveWork) {
        return leaveWorkMapper.insertSelective(leaveWork);
    }

    //用户查询未审批请假申请
    @Override
    public List<LeaveWork> noAuditUserLeave(String userId) {
        return leaveWorkMapper.noAuditById(userId);
    }

    //用户查询请假信息
    @Override
    public List<LeaveWork> queryUserLeaveByLike(HashMap<String, Object> map) {
        return leaveWorkMapper.selectUserLeaveByLike(map);
    }
}
