package com.system.attendance.service.impl;

import com.system.attendance.mapper.AttendanceErrMapper;
import com.system.attendance.mapper.AttendanceMapper;
import com.system.attendance.model.Attendance;
import com.system.attendance.model.AttendanceErr;
import com.system.attendance.service.IAttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AttendanceService implements IAttendanceService {

    private static final Logger LOG = LoggerFactory.getLogger(AttendanceService.class);

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private AttendanceErrMapper attendanceErrMapper;


    /**
     * 获取正确考勤所有信息
     * @return
     */
    @Override
    public List<Attendance> getAllAttend() {
        return attendanceMapper.selectAll();
    }

    /**
     * 获取异常考勤所有信息
     * @return
     */
    @Override
    public List<AttendanceErr> getAllERRAttend() {
        return attendanceErrMapper.selectAll();
    }

    /**
     * 统计记录条数
     * @return
     */
    @Override
    public int selectRightCount() {
        return attendanceMapper.selectCount();
    }

    @Override
    public int selectErrCount() {
        return attendanceErrMapper.selectErrCount();
    }

    /**
     * 今日打卡信息
     * @param time
     * @return
     */
    @Override
    public List<Attendance> getTodayAttend(String time) {
        return attendanceMapper.getToday(time);
    }

    //通过id获取异常表该条记录所有信息
    @Override
    public AttendanceErr getErrAttendById(String attendId) {
        return attendanceErrMapper.selectByPrimaryKey(attendId);
    }
    //将异常考勤信息插入正常表
    @Override
    public int insertErrToRight(AttendanceErr record) {
        return attendanceMapper.insertErrSelective(record);
    }
    //插入正常表成功后，通过id删除异常表信息
    @Override
    public int deleteRightErr(String attendId) {
        return attendanceErrMapper.deleteByPrimaryKey(attendId);
    }
    //如果管理员审批为同意，则将正常表状态改为1
    @Override
    public int updateStatus(String attendId) {
        return attendanceMapper.updateStatus(attendId);
    }

    //通过姓名，部门，考勤时间查询考勤信息
    @Override
    public List<Attendance> queryByLike(HashMap<String, Object> map) {
        return attendanceMapper.selectByLike(map);
    }

    //用户通过时间段搜索考勤信息
    @Override
    public List<Attendance> userQueryByLike(HashMap<String, Object> map) {
        return attendanceMapper.userSelectByLike(map);
    }

    //用户通过userId查询自己的考勤信息
    @Override
    public List<Attendance> getAttendById(String userId) {
        return attendanceMapper.selectByUserId(userId);
    }

    //新增考勤记录
    @Override
    public int addOneAttend(Attendance record) {
        return attendanceMapper.insertSelective(record);
    }

    //统计考勤id是否存在
    @Override
    public int countAttendById(String attendId) {
        return attendanceMapper.countById(attendId);
    }

    //用户提交异常考勤原因
    @Override
    public int userSayWhyErr(String attendId,String remarks) {
        return attendanceMapper.updateRemark(attendId,remarks);
    }
}
