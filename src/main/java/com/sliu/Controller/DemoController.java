package com.sliu.Controller;

import com.sliu.WebSocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * 后台进行消息推送
     */
        @GetMapping("/testSocket")
    public void testSocket() {
        System.out.print("测试推送");
        simpMessagingTemplate.convertAndSend("/topic/hello","11111111111");
    }
}