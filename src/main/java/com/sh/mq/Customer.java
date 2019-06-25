package com.sh.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Customer implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setInstanceName("Consumer");

        /**
         * 订阅指定topic下tags分别等于Tag1或Tag3的消息
         */
        consumer.subscribe("Topic1",
                "Tag1 || Tag3");

        /**
         * 订阅指定topic下所有消息
         * 注意：一个consumer对象可以订阅多个topic
         */
        consumer.subscribe("Topic2",
                "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                System.out.println(Thread.currentThread().getName()
                        + " Receive New Message: " + msgs.size());

                MessageExt msg = msgs.get(0);
                if ("Topic1".equals(msg.getTopic())) {
                    if (null != msg.getTags()) {
                        switch (msg.getTags()) {
                            case "Tag1" :
                                System.out.println("aaa");
                                break;
                            case "Tag3" :
                                System.out.println("bbb");
                                break;
                        }
                    }
                }

                if ("Topic2".equals(msg.getTopic())) {
                    System.out.println(new String(msg.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            }
        });

        consumer.start();
    }
}
