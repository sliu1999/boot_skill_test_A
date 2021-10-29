package com.sliu.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 消息推送配置 点对点的用SimpMessagingTemplate 群发可以用@SendTo
 * EnableWebSocketMessageBroker开启消息代理
 */
@Configuration
@EnableWebSocketMessageBroker
public class socketTemplateConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //设置消息代理的前缀 及如果消息前缀是/topic,就会将消息转发给消息代理broker,再由消息代理广播给当前连接的客户端
        config.enableSimpleBroker("/topic");
        //配置一个或多个前缀，通过这些前缀过滤出需要被注解处理的消息，前缀为/app的destination可以通过
        //@MessageMapping注解的方法处理，而其他的/topic 直接交给broker处理
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //定义一个前缀为/my-websocket的endPoint 并开启sockjs支持（解决浏览器对websocket兼容），客户端通过这里配置的URL来建立WebSocket连接
        registry.addEndpoint("/my-websocket").setAllowedOrigins("*").withSockJS();
    }

}
