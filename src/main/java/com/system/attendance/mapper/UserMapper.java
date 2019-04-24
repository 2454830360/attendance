package com.system.attendance.mapper;

import com.system.attendance.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    //查密码
    String userPassword(String userId);

    //通过姓名、部门模糊查询
    List<User> selectByLike(User record);

    //所有用户信息
    List<User> selectAll();

    //查询用户是否存在
    int countByUserId(String userId);

    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}