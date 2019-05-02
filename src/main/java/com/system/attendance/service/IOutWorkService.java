package com.system.attendance.service;

import com.system.attendance.model.OutWork;

import java.util.HashMap;
import java.util.List;

public interface IOutWorkService {

    List<OutWork> getAllOut();
    List<OutWork> queryByUserId(String userId);
    List<OutWork> queryOutingByUserId(String userId,String time);
    List<OutWork> queryByLike(HashMap<String,Object> map);
    int addOneOut(OutWork outWork);
}
