package com.sh.controller;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.sh.mq.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mq")
@Api(value = "消息队列公共接口", tags = {"消息队列公共接口Api文档"})
public class MqController {

    @Autowired
    private Producer producer;

    @RequestMapping("/push")
    @ApiOperation(value = "消息推送接口", notes = "消息推送接口", httpMethod = "GET")
    public String push() throws MQClientException, InterruptedException {
        producer.produce();
        return "success";
    }
}
