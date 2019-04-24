package com.system.attendance.service;

public interface IAdminService {

    int selectByAdminId(String adminId);
    String verifyAdminByPassword(String adminId);

}
