package com.system.attendance.mapper;

import com.system.attendance.model.LeaveWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface LeaveWorkMapper {

    //通过姓名、部门、时间模糊查询请假信息
    List<LeaveWork> selectByLike(HashMap<String,Object> map);

    //用户通过时间查询请假信息
    List<LeaveWork> selectUserLeaveByLike(HashMap<String,Object> map);

    //管理员同意请假审批
    int updateStatusToYes(String leaveWorkId);

    //管理员不同意请假审批
    int updateStatusToNo(String leaveWorkId);

    //未通过审批请假信息
    List<LeaveWork> selectNoApply();

    //已通过审批请假信息
    List<LeaveWork> selectAll();

    int deleteByPrimaryKey(String leaveWorkId);

    int insert(LeaveWork record);

    int insertSelective(LeaveWork record);

    LeaveWork selectByPrimaryKey(String leaveWorkId);

    int updateByPrimaryKeySelective(LeaveWork record);

    int updateByPrimaryKey(LeaveWork record);

    List<LeaveWork> noAuditById(String userId);
}