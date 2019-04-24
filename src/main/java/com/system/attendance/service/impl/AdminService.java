package com.system.attendance.service.impl;

import com.system.attendance.mapper.AdminMapper;
import com.system.attendance.service.IAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminMapper adminMapper;

    //查询管理员id是否存在
    @Override
    public int selectByAdminId(String adminId) {
        return adminMapper.countByAdminId(adminId);
    }

    //通过密码验证管理员登录
    @Override
    public String verifyAdminByPassword(String adminId) {
        return adminMapper.adminPassword(adminId);
    }
}
