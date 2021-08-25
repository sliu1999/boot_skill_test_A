package com.sliu.mq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class PublisherConfireAndReturnConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    Logger logger = LoggerFactory.getLogger(PublisherConfireAndReturnConfig.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initMethod(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        System.out.print("/****************"+s+"/"+ack);
        if(ack){
            logger.info("--------消息发送(到交换机)成功");
        }else{
            logger.warn("--------消息发送(到交换机)失败");
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info(message+"/"+i+"/"+s+"/"+s1+"/"+s2);
        logger.info("~~~~~~~~消息发送到交换机但未分发到队列！！！");
    }

}