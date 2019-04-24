package com.system.attendance.controller;


import com.system.attendance.utils.JWTUtil;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;


@RequestMapping("/test/*")
@RestController
public class TestController {

    @RequestMapping("test")
    public String testLogin() throws FileNotFoundException {
        String token = JWTUtil.getToken();
        System.out.println("token:"+token);

        File paths = new File(ResourceUtils.getURL("classpath:").getPath());
        String parent = paths.getParentFile().getParentFile().getParent()+File.separator;
        parent = parent.substring(5);
        System.out.println("parent:"+parent);
        System.out.println(paths);

        return token;
    }


}
