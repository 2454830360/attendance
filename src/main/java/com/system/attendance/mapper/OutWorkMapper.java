package com.system.attendance.mapper;

import com.system.attendance.model.OutWork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface OutWorkMapper {

    List<OutWork> selectByLike(HashMap<String,Object> map);

    List<OutWork> selectAll();

    List<OutWork> selectById(String userId);

    List<OutWork> selectIngById(@Param("userId") String userId, @Param("time") String time);

    int deleteByPrimaryKey(String outWorkId);

    int insert(OutWork record);

    int insertSelective(OutWork record);

    OutWork selectByPrimaryKey(String outWorkId);

    int updateByPrimaryKeySelective(OutWork record);

    int updateByPrimaryKey(OutWork record);
}