package com.system.attendance.controller;

import com.system.attendance.model.OutWork;
import com.system.attendance.model.User;
import com.system.attendance.service.impl.OutWorkService;
import com.system.attendance.service.impl.UserService;
import com.system.attendance.utils.TimeUtil;
import com.system.attendance.utils.UUIDUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/out/*")
public class OutWorkController {

    private static final Logger LOG = LoggerFactory.getLogger(OutWorkController.class);

    @Autowired
    private OutWorkService outWorkService;
    @Autowired
    private UserService userService;

    /**
     * 获取所有出差信息
     * @return
     */
    @RequestMapping("getAll")
    public List<OutWork> getAllOut(){
        return outWorkService.getAllOut();
    }


    //通过姓名+部门+时间查询出差信息
    @RequestMapping("query")
    public List<OutWork> queryByLike(@RequestBody JSONObject json){
        HashMap<String,Object> maps = new HashMap<String,Object>();
        String userName = null;
        String dept = null;
        String beginTime = null;
        String endTime = null;

        if(json.has("user_name")&&!(("").equals(json.getString("user_name")))){
            userName = json.getString("user_name");
        }
        if(json.has("dept")&&!(("").equals(json.getString("dept")))){
            dept = json.getString("dept");
        }
        if(json.has("beginTime")&&!(("").equals(json.getString("beginTime")))){
            beginTime = json.getString("beginTime");
        }
        if(json.has("endTime")&&!(("").equals(json.getString("endTime")))){
            endTime = json.getString("endTime");
        }
        maps.put("userName",userName);
        maps.put("dept",dept);
        maps.put("beginTime",beginTime);
        maps.put("endTime",endTime);
        LOG.info("查询出差信息:"+userName+"-"+dept+"-"+beginTime+"-"+endTime);

        List<OutWork> outWorkList = outWorkService.queryByLike(maps);

        return outWorkList;
    }

    //新增出差任务书
    @RequestMapping("addOne")
    public List<OutWork> addOutWork(@RequestBody JSONObject json){
        OutWork outWork = new OutWork();
        String outWorkId = UUIDUtil.createOutWorkId();
        String userId = null;
        String userName = null;
        String dept = null;
        String outBeginTime = null;
        String outEndTime = null;
        String outDay = null;
        String outReason = null;
        String outStatus = "1";

        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(json.has("out_begin_time")&&!(("").equals(json.getString("out_begin_time")))){
            outBeginTime = json.getString("out_begin_time");
        }
        if(json.has("out_end_time")&&!(("").equals(json.getString("out_end_time")))){
            outEndTime = json.getString("out_end_time");
        }
        if(json.has("out_reason")&&!(("").equals(json.getString("out_reason")))){
            outReason = json.getString("out_reason");
        }
        if(userId!=null){
            User userById = userService.getOneUserById(userId);
            userName = userById.getUserName();
            dept = userById.getDept();
        }

        outWork.setOutWorkId(outWorkId);
        outWork.setUserId(userId);
        outWork.setUserName(userName);
        outWork.setDept(dept);
        outWork.setOutBeginTime(outBeginTime);
        outWork.setOutEndTime(outEndTime);
        outWork.setOutReason(outReason);
        outWork.setOutStatus(outStatus);

        int i = outWorkService.addOneOut(outWork);
        if(i == 1){
            LOG.info("出差任务书下发成功！outWorkId："+outWorkId);
            return outWorkService.getAllOut();
        }
        return outWorkService.getAllOut();
    }


    //用户进行中的出差任务
    @RequestMapping("userOutIng")
    public List<OutWork> userQueryOutIng(@RequestBody JSONObject json){
        String userId = null;
        String time = TimeUtil.todayStringTime();
        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(userId != null){
            return outWorkService.queryOutingByUserId(userId,time);
        }else{
            LOG.info("用户查询出差记录出错！userId为空");
            return null;
        }
    }

    //用户已完成的出差任务
    @RequestMapping("userOutEnd")
    public List<OutWork> userQueryOutEnd(@RequestBody JSONObject json){
        String userId = null;

        if(json.has("user_id")&&!(("").equals(json.getString("user_id")))){
            userId = json.getString("user_id");
        }
        if(userId != null){
            List<OutWork> outWorks = outWorkService.queryByUserId(userId);
            return outWorks;
        }else{
            LOG.info("用户查询出差记录出错！userId为空");
            return null;
        }
    }

}
