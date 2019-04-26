package com.system.attendance.controller;

import com.system.attendance.model.Admin;
import com.system.attendance.model.User;
import com.system.attendance.service.impl.AdminService;
import com.system.attendance.service.impl.UserService;
import com.system.attendance.utils.JWTUtil;
import com.system.attendance.websocket.WebSocketServer;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//登录验证
@RestController
@RequestMapping("/login/*")
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    //用户登录验证
    @RequestMapping("user")
    public Map<String,String> userLoginVerify(@RequestBody JSONObject json){
        Map<String,String> map = new HashMap<>();
        String status;
        String token = null;
        String userId = null;
        String userPassword = null;
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(json.has("user_password")&&!(("").equals(json.getString("user_password")))){
            userPassword = json.getString("user_password");
        }
        if(userId != null && userPassword != null){
            int i = userService.selectByUserId(userId);
            if(i == 1){
                String passwordInDB = userService.verifyUserByPassword(userId);
                if(userPassword.equals(passwordInDB)){
                    token = "tokens:"+JWTUtil.getToken();
                    status = "true";
                    LOG.info("用户登录成功，userId----"+userId);
                }else{
                    status = "password_error";
                    LOG.info("密码错误，userId----"+userId);
                }
            }else{
                status = "user_error";
                LOG.info("用户id不存在！userId----"+userId);
            }
        }else{
            status = "null";
            LOG.info("用户id为空！userId----"+userId);
        }
        map.put("status",status);
        if(token != null){
            map.put("token",token);
        }
        return map;
    }

    //管理员登录验证
    @RequestMapping("admin")
    public Map<String,String> adminLoginVerify(@RequestBody JSONObject json){
        Map<String,String> map = new HashMap<>();
        String status;
        String token = null;
        String adminId = null;
        String adminPassword = null;
        if(json.has("admin_id")&&!(("").equals(json.getString("admin_id")))){
            adminId = json.getString("admin_id");
        }
        if(json.has("admin_password")&&!(("").equals(json.getString("admin_password")))){
            adminPassword = json.getString("admin_password");
        }
        if(adminId != null && adminPassword != null){
            int i = adminService.selectByAdminId(adminId);
            if(i == 1){
                String passwordInDB = adminService.verifyAdminByPassword(adminId);
                if(adminPassword.equals(passwordInDB)){
                    status = "true";
                    token = "tokens:"+JWTUtil.getToken();
                    LOG.info("管理员登录成功，adminId----"+adminId);
                    LOG.info("管理员登录生成的token----"+token);
                }else{
                    LOG.info("密码错误，adminId----"+adminId);
                    status = "password_error";
                }
            }else{
                status = "admin_error";
                LOG.info("管理员id不存在！adminId----"+adminId);
            }
        }else{
            status = "null";
            LOG.info("管理员id为空！adminId----"+adminId);
        }
        map.put("status",status);
        if(token != null){
            map.put("token",token);
        }
        return map;
    }

    //登出
    @RequestMapping("out")
    public String loginOut(HttpServletRequest request) {

        return "";
    }

}
