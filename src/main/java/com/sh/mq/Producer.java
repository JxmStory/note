package com.sh.mq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Producer {

    public void produce() throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setProducerGroup("ProducerGroup");
        producer.setInstanceName("Producer");
        producer.start();

        try {

            {
                for (int i = 0; i < 20; i++) {
                    int orderId = i % 3;
                    Message message = new Message("Topic1", "Tag" + orderId,
                            "Username", ("Shh-" + i).getBytes());
                    SendResult result = producer.send(message, new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            Integer i = (Integer) arg;
                            Integer index = i % mqs.size();
                            return mqs.get(index);
                        }
                    }, orderId);
                    System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
                }
            }

            {
                Message message = new Message("Topic2", "TagA",
                        "Username", "Shh".getBytes());
                producer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("success");
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.println("error");
                    }
                });
            }
        } catch (Exception e) {
            TimeUnit.MILLISECONDS.sleep(1000);
        }

        producer.shutdown();
    }
}
