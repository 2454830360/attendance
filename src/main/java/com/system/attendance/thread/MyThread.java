package com.system.attendance.thread;

import com.system.attendance.utils.DBUtil;
import com.system.attendance.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 线程先对数据库中的数据查询一次，存在sum变量中，
 * 然后再一直对数据库中的数据进行轮询，
 * new_sum大于sum的话就发送消息给WebSocket实现类
 */
public class MyThread implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(MyThread.class);

    private int sum; //原始条数
    private String sign_out_time;//原始签到时间
    private String new_out_time;//轮询搜索数据库条数
    private int new_sum; //轮询搜索数据库条数
    private boolean stop = true;
    public void stop(){
        stop = false;
    }

    @Override
    public void run() {
        sum = DBUtil.getCount();
        WebSocketServer wss = new WebSocketServer();
        while (stop){
            new_sum = DBUtil.getCount();
            if(sum < new_sum){
                LOG.info("今日考勤数据库新增记录啦！");
                sum = new_sum;
                wss.onMessage(sum);
            }
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


}
