package com.system.attendance.service;

import com.system.attendance.model.OutWork;

import java.util.HashMap;
import java.util.List;

public interface IOutWorkService {

    List<OutWork> getAllOut();
    List<OutWork> queryByLike(HashMap<String,Object> map);
    int addOneOut(OutWork outWork);
}
