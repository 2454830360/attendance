package com.system.attendance.mapper;

import com.system.attendance.model.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    //查询号码
    String adminPassword(String adminId);

    //统计数量
    int countByAdminId(String adminId);

    int deleteByPrimaryKey(String adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String adminId);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}