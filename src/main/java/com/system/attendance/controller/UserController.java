package com.system.attendance.controller;

import com.system.attendance.model.User;
import com.system.attendance.service.impl.UserService;
import com.system.attendance.utils.TimeUtil;
import com.system.attendance.utils.UUIDUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/*")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户信息
     * @return
     */
    @RequestMapping("getAll")
    public List<User> getAll(){
        return userService.getAll();
    }

    //新增用户
    @RequestMapping("addOne")
    // @ResponseBody  该注解表明该方法返回值均自动转为json格式传给前端
    public List<User> addUser(@RequestBody JSONObject json){
        String userId = UUIDUtil.createUserId();
        User user = new User();
        user.setUserId(userId);
        user.setUserCreateTime(TimeUtil.createTime());
        String user_name=null;
        if(json.has("user_password")&&!(("").equals(json.getString("user_password")))){
            user.setUserPassword(json.getString("user_password"));
        }
        if(json.has("user_name")&&!(("").equals(json.getString("user_name")))){
            user_name = json.getString("user_name");
            user.setUserName(user_name);
        }
        if(json.has("user_sex")&&!(("").equals(json.getString("user_sex")))){
            user.setUserSex(json.getString("user_sex"));
        }
        if(json.has("user_age")&&!(("").equals(json.getString("user_age")))){
            user.setUserAge(json.getString("user_age"));
        }
        if(json.has("user_phone")&&!(("").equals(json.getString("user_phone")))){
            user.setUserPhone(json.getString("user_phone"));
        }
        if(json.has("dept")&&!(("").equals(json.getString("dept")))){
            user.setDept(json.getString("dept"));
        }
        if(json.has("user_address")&&!(("").equals(json.getString("user_address")))){
            user.setUserAddress(json.getString("user_address"));
        }

        if(user_name != null && !(("").equals(user_name))){
            userService.addUser(user);
            LOG.info("新增用户成功,user_name="+user_name);
        }else {
            LOG.info("新增用户失败,user_name="+user_name);
        }

        return userService.getAll();
    }

    //删除用户
    @RequestMapping("deleteOne")
    public List<User> deleteUser(@RequestBody JSONObject json){
        //String user_id = request.getParameter("user_id");
        String user_id = null;
        if(json.has("user_id")){
            user_id = json.getString("user_id");
        }
        if(null != user_id && !(("").equals(user_id))){
            if(userService.selectByUserId(user_id) == 1){
                int i = userService.deleteUser(user_id);
                if(i == 1){
                    LOG.info("删除用户成功："+user_id);
                    return userService.getAll();
                }
            }else {
                LOG.info("删除失败，用户不存在！user_id="+user_id);
            }
        }else {
            LOG.info("删除失败！user_id="+user_id);
        }
        return userService.getAll();
    }

    //修改用户信息
    @RequestMapping("updateOne")
    public List<User> updateUser(@RequestBody JSONObject json){
        User user = new User();
        String user_id = null;
        String user_name = null;

        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            user_id = json.getString("user_id");
            user.setUserId(user_id);
        }
        if(json.has("user_password")&&!(("").equals(json.getString("user_password")))){
            user.setUserPassword(json.getString("user_password"));
        }
        if(json.has("user_name")&&!(("").equals(json.getString("user_name")))){
            user_name = json.getString("user_name");
            user.setUserName(user_name);
        }
        if(json.has("user_sex")&&!(("").equals(json.getString("user_sex")))){
            user.setUserSex(json.getString("user_sex"));
        }
        if(json.has("user_age")&&!(("").equals(json.getString("user_age")))){
            user.setUserAge(json.getString("user_age"));
        }
        if(json.has("user_phone")&&!(("").equals(json.getString("user_phone")))){
            user.setUserPhone(json.getString("user_phone"));
        }
        if(json.has("dept")&&!(("").equals(json.getString("dept")))){
            user.setDept(json.getString("dept"));
        }
        if(json.has("user_create_time")&&!(("").equals(json.getString("user_create_time")))){
            user.setUserCreateTime(json.getString("user_create_time"));
        }
        if(json.has("user_address")&&!(("").equals(json.getString("user_address")))){
            user.setUserAddress(json.getString("user_address"));
        }
        if(json.has("user_image")&&!(("").equals(json.getString("user_image")))){
            user.setUserImage(json.getString("user_image"));
        }

        int i = userService.updateUser(user);
        if (i == 1){
            LOG.info("修改用户信息成功！user_id："+user_id+"-user_name："+user_name);
            return userService.getAll();
        }else {
            LOG.info("修改用户信息失败！user_id："+user_id+"-user_name："+user_name);
        }
        return userService.getAll();
    }

    //通过姓名、部门进行模糊查询
    @RequestMapping("query")
    public List<User> queryUser(@RequestBody JSONObject json){
        User user = new User();
        if(json.has("user_name")&&!(("").equals(json.getString("user_name")))){
            String user_name = json.getString("user_name");
            user.setUserName(user_name);
        }
        if(json.has("dept")&&!(("").equals(json.getString("dept")))){
            String dept = json.getString("dept");
            user.setDept(dept);
        }

        return userService.getByLike(user);
    }

    //通过id查询一个用户的信息
    @RequestMapping("getOne")
    public User getOneUser(@RequestBody JSONObject json){
        String user_id = null;
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            user_id = json.getString("user_id");
        }
        if(user_id != null ){
            User userById = userService.getOneUserById(user_id);
            System.out.println(userById.toString());
            return userById;
        }else{
            LOG.info("user_id为空");
            return null;
        }
    }


}
