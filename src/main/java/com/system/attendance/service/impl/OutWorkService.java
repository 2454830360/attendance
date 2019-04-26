package com.system.attendance.service.impl;


import com.system.attendance.mapper.OutWorkMapper;
import com.system.attendance.model.OutWork;
import com.system.attendance.service.IOutWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OutWorkService implements IOutWorkService {

    @Autowired
    private OutWorkMapper outWorkMapper;


    /**
     * 获取所有出差信息
     * @return
     */
    @Override
    public List<OutWork> getAllOut() {
        return outWorkMapper.selectAll();
    }

    //通过姓名部门时间模糊查询出差信息
    @Override
    public List<OutWork> queryByLike(HashMap<String, Object> map) {
        return outWorkMapper.selectByLike(map);
    }

    //新增出差任务书
    @Override
    public int addOneOut(OutWork outWork) {
        return outWorkMapper.insertSelective(outWork);
    }

    //用户查询自己的出差记录
    @Override
    public List<OutWork> queryByUserId(String userId) {
        return outWorkMapper.selectById(userId);
    }
}
