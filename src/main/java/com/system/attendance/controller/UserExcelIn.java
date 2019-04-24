package com.system.attendance.controller;

import com.system.attendance.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/excel_in/*")
public class UserExcelIn {

    private static final Logger LOG = LoggerFactory.getLogger(UserExcelIn.class);

    @Autowired
    private UserService userService;

    /**
     * 批量导入用户
     * @param file
     * @return
     */
    @PostMapping("user")
    public boolean importUser(@RequestParam(value="file",required = false) MultipartFile file) {
        boolean status = false;
        if (null == file){
            LOG.error("上传文件为空！");
        }
        String fileName = file.getOriginalFilename();
        try {
            status = userService.bulkImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
