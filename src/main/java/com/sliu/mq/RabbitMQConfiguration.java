package com.sliu.mq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class RabbitMQConfiguration {

    /**
     * 简单队列 不绑定交换机，可用户simple工作模式
     * @return
     */

    @Bean
    public Queue queue() {
        return new Queue("wfx-queue");
    }


    ////////////////////////////////////////////////////////////////

    /**
     * 发布订阅模式
     * @return
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue("wfx-fanout-queue");
    }
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("wfx-fanout-queue2");
    }
    /**
     * 声明交换机,fanout 类型
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange("fanoutExchange");
        return fanoutExchange;
    }
    /**
     * 将队列和交换机绑定
     * 将wfx-fanout-queue队列和fanoutExchange交换机绑定
     */
    @Bean
    public Binding bindingFanoutExchange(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }
    @Bean
    public Binding bindingFanoutExchange2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * 路由模式
     * @return
     */
    @Bean
    public Queue directQueue1() {
        return new Queue("wfx-direct-queue1");
    }
    @Bean
    public Queue directQueue2() {
        return new Queue("wfx-direct-queue2");
    }
    /**
     * 声明交换机,direct 类型
     */
    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange("directExchange");
        return directExchange;
    }
    /**
     * 将队列和交换机绑定
     * 将wfx-direct-queue1队列和directExchange交换机通过routeKey rk1绑定
     * 当带有routeKeY为rk1的消息发送到directExchange交换机，会把此消息发送到wfx-direct-queue1
     */
    @Bean
    public Binding bindingDirectExchange(Queue directQueue1, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue1).to(directExchange).with("rk1");
    }

    @Bean
    public Binding bindingDirectExchange2(Queue directQueue2, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue2).to(directExchange).with("rk2");
    }


    ////////////////////////////////////////////////////////////


    /**
     * 死信队列
     * @return
     */
    @Bean
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange("deadLetterExchange").durable(true).build();
    }

    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    声明  死信交换机
        args.put("x-dead-letter-exchange", "deadLetterExchange");
//       x-dead-letter-routing-key    声明 死信路由键    当死信队列超过时间，通过此交换机转发到路由KEY为 KEY_R的队列上
        args.put("x-dead-letter-routing-key", "KEY_R");
        return QueueBuilder.durable("deadLetterQueue").withArguments(args).build();
    }

    @Bean
    public Queue redirectQueue() {
        return QueueBuilder.durable("redirectDeadQueue").build();
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding("deadLetterQueue", Binding.DestinationType.QUEUE, "deadLetterExchange", "DL_KEY", null);

    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding("redirectDeadQueue", Binding.DestinationType.QUEUE, "deadLetterExchange", "KEY_R", null);
    }



    /////////////////////////////////////

}
