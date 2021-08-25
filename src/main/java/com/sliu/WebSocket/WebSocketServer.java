package com.sliu.WebSocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * ws://localhost:8080/websocket/1/cc11c6de-5af6-11ea-aaa9-00163f00dc26
 * */
@Slf4j
@ServerEndpoint(value = "/websocket/{platformType}/{userId}")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    public static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    public Session session;

    //接收参数中的用户ID
    public String userId;

    //接收用户中的平台类型
    public String platformType;


    /**
     * 连接建立成功调用的方法
     * 接收url中的参数
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("platformType") String platformType, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        this.platformType = platformType;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1

        List<String> userIdList = new ArrayList<>();
        log.info("有新连接加入！当前在线人数为" + getOnlineCount() + "  userId==== " + userId + "  platformType==== " + platformType);
        try {
            for (WebSocketServer item : webSocketSet) {
                userIdList.add(item.userId);
            }
            log.info(userIdList.toString());
            //sendMessage("当前人数"+userIdList);//发给自己


            HashMap message = new HashMap(2);
            message.put("msgType","1");
            message.put("fromUserId",userId);
            message.put("nowUserId",userIdList);
            sendInfos(JSON.toJSONString(message));//群发所有某人上线，包括自己
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法(页面关闭)
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        try {
            List<String> userIdList = new ArrayList<>();
            for (WebSocketServer item : webSocketSet) {
                userIdList.add(item.userId);
            }
            log.info(userIdList.toString());
            HashMap message = new HashMap(2);
            message.put("msgType","2");
            message.put("fromUserId",userId);
            message.put("nowUserId",userIdList);
            sendInfos(JSON.toJSONString(message));//群发所有某人下线，包括自己
        }catch (Exception e){
            log.error("websocket IO异常");
        }

        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject jsonObject = JSON.parseObject(message);
        String toUserId = jsonObject.getString("toUserId");
        String contentText = jsonObject.getString("contentText");
        log.info("来自"+userId+"的消息：" + contentText+"发送至："+toUserId);
        try {
            HashMap param = new HashMap(4);
            param.put("msgType","3");
            param.put("fromUserId",userId);
            param.put("contentText",contentText);
            if ("all".equals(toUserId)) {
                //发送给所有人
                param.put("toUserId","all");
                sendInfos(JSON.toJSONString(param));//群发所有某人下线，包括自己
            }else {
                param.put("toUserId",toUserId);
                sendInfo(toUserId,JSON.toJSONString(param));//发送给某人
            }
            log.info(JSON.toJSONString(param));
        }catch (Exception e){
            log.error("websocket IO异常");
        }

    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误" + error);
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 私发
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String userId, String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                if (item.userId.equals(userId)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfos(String message) throws IOException {
        log.info(message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
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
