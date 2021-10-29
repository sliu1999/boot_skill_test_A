package com.sliu.Service.impl;

import com.sliu.Service.MqService;
import com.sliu.Service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class MqImpl implements MqService {

    @Autowired
    private AmqpTemplate amqpTemplate;



    @Override
    public void sendMsg(String message) throws IOException {
        //simple模式 队列名，消息体   直接发消息到队列上
        //amqpTemplate.convertAndSend("wfx-queue",message);

        //交换机名，routekey,消息体
        //amqpTemplate.convertAndSend("directExchange", "rk1", message);

        //amqpTemplate.convertAndSend("fanoutExchange", null, message);

        //交换机名，routekey,消息体
        //amqpTemplate.convertAndSend("delay_exchange", "k1", message);




    }

    //RabbitTemplate 实现了 AmqpTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //防止消息重复消费，可加messageId
    @Override
    public void contextLoads(){

        String m = UUID.randomUUID().toString();
        System.out.print(m);
        String json = "红色大狼狗";
        Message message = MessageBuilder.withBody(json.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("UTF-8").setMessageId(m).build();
        rabbitTemplate.convertAndSend("fanoutExchange",null,message);

    }

    /**
     * 延迟队列实现 通过设置消息ttl存活时间
     * 一个交换机direct 2个队列 k1  , k2
     * queue 1 -k1为死信队列 设置消息ttl存活时间，设置消息过期后转发的交换机，设置消息过期后转发到交换机对应的k-k2
     * @param msg
     */
    @Override
    public void sendDelayMessage(String msg) {
        //这里的消息可以是任意对象，无需额外配置，直接传即可
        log.info("===============延时队列生产消息====================");
        log.info("发送时间:{},发送内容:{}", LocalDateTime.now(), msg);
        this.rabbitTemplate.convertAndSend(
                "delay_exchange",
                "k1",
                msg,
                message -> {
                    //注意这里时间要是字符串形式
                    message.getMessageProperties().setExpiration("60000");
                    return message;
                }
        );
        log.info("{}ms后执行", 60000);
    }
}
