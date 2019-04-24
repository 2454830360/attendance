package com.system.attendance.controller;

import com.system.attendance.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/excel_out/*")
public class UserExcelOut {

    @Autowired
    private UserService userService;

    /**
     * 导出用户excel表
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value = "user",produces = "application/json")
    public String exportUser(HttpServletResponse response) throws Exception {
        return userService.exportUserFromDB(response);
    }

}
