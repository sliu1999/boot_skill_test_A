package com.sliu.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sliu.WebSocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class DemoController {

    //spring boot中操作WebSocket的类
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 通过接口方式发消息
     * @param message
     * @param toUserId
     * @return
     * @throws IOException
     */
    @RequestMapping("/push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message,toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    /**
     * 后台进行消息推送 点对点的消息发送
     */
        @GetMapping("/testSocket")
    public void testSocket() {
        System.out.print("测试推送");
        //topic开头的消息会到代理，由代理广播
        simpMessagingTemplate.convertAndSend("/topic/hello","11111111111");
    }

    /**
     * 接收/app/hello消息
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public JSON getMessage(String message) {
        System.out.print("测试推送"+message);
        JSON json = new JSONObject();
        ((JSONObject) json).put("name","测试消息发送");
        return json;
    }


}