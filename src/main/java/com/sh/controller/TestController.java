package com.sh.controller;

import com.sh.juc.AsyncService;
import com.sh.service.inter.UserServiceInter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AsyncService asyncService;
    @Autowired
    private UserServiceInter userService;

    @GetMapping("async")
    public String async(String[] phone) {
        logger.info("【测试异步调用】 开始");
        asyncService.sendMessage(phone);
        logger.info("【测试异步调用】 结束");
        return "success";
    }

    @GetMapping("addList")
    public String addList() {
        userService.addList();
        return "success";
    }
}
