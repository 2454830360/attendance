package com.system.attendance.websocket;

import com.system.attendance.model.Attendance;
import com.system.attendance.thread.MyThread;
import com.system.attendance.utils.DBUtil;
import com.system.attendance.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocketserver")
@Component
public class WebSocketServer {

    MyThread threads = new MyThread();
    Thread thread = new Thread(threads);

    //打印日志
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法*
     */

    @OnOpen
    public void onOpen(Session session) throws IOException{
        //设置只能有一个用户连接，后续增加用户再说吧
        if(getOnlineCount() == 0){
            this.session = session;
            webSocketSet.add(this);     //加入set中
            addOnlineCount();           //在线数加1
            LOG.info("--管理员登录webSocket，当前在线人数为--" + getOnlineCount());
//            this.sid=sid;
            //开户一个线程对数据库中的数据进行轮询
            thread.start();
        }
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        //设置只能有一个用户连接，后续增加用户再说吧
        if(getOnlineCount() == 1){
            this.session.close();
            threads.stop();
            webSocketSet.remove(this);  //从set中删除
            subOnlineCount();           //在线数减1
            LOG.info("有一管理员退出webSocket，当前在线人数为：" + getOnlineCount());
        }
    }

    /**
     *  数据库发生变化时给服务器发送消息
     * @param count
     */

    @OnMessage
    public void onMessage(int count) {
        LOG.info("有人打卡啦！今日总考勤数："+count);
        //群发消息
        try {
            sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("webSocket发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法是根据需要添加的
     * 服务器返回“1”，告诉客户端数据库更新了
     */
    public void sendMessage() throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                List<Attendance> todayAttend = DBUtil.getTodayAttend();
                String json = JsonUtil.listToJson(todayAttend);
                item.session.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
