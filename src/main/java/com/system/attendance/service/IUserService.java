package com.system.attendance.service;

import com.system.attendance.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    List<User> getAll();
    boolean bulkImport(String fileName, MultipartFile file) throws Exception;
    int addUser(User user);
    int deleteUser(String userId);
    int updateUser(User user);
    int selectByUserId(String userId);
    List<User> getByLike(User user);
    String verifyUserByPassword(String userId);
    User getOneUserById(String userId);
}
