package com.sh.controller;

import com.sh.common.redis.RedisUtil;
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
    @Autowired
    private RedisUtil redisUtil;

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

    @GetMapping("rds")
    public String rdsTest(String name, String value) {
        redisUtil.set(name, value);
        return name + ":" + redisUtil.get(name);
    }

    @GetMapping("lock")
    public String tryLock(String name, String value) throws InterruptedException {
        Boolean flag = redisUtil.tryLock(name, value, 5000);
        if (flag) {
            logger.info("redis加锁成功");
        } else {
            logger.info("redis加锁失败");
            return "try again later!";
        }
        Thread.sleep(3000);
        redisUtil.unlock(name, value);
        return "success";
    }

    @GetMapping("block")
    public String blockLock(String name, String value) throws InterruptedException {
        Boolean flag = redisUtil.lock(name, value, 10000, 3000);
        if (flag) {
            logger.info("redis加锁成功");
        } else {
            logger.info("redis加锁失败");
            return "try again later!";
        }
        Thread.sleep(5000);
        redisUtil.unLockLua(name, value);
        return "success";
    }

}
