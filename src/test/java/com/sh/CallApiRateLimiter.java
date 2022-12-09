package com.sh;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.common.util.concurrent.RateLimiter;

public class CallApiRateLimiter {

    // 创建令牌桶：每分钟30个令牌，容量2（允许短暂突发）
    private static final RateLimiter rateLimiter = RateLimiter.create(0.1);


    public static void main(String[] args) throws InterruptedException {


        // 模拟高频调用
        for (int i = 0; i < 60; i++) {
            // 获取令牌（无超时等待）
            rateLimiter.acquire(); // 若需设置超时：rateLimiter.tryAcquire(timeout, timeUnit)

            try {
                // 实际调用第三方接口代码
                System.out.println("成功调用: " + i + ", 时间:" + LocalDateTimeUtil.now());
            } catch (Exception e) {
                System.err.println("调用失败: " + e.getMessage());
            }
            Thread.sleep(500); // 每0.5秒调用一次（超过限流阈值）
        }
    }
}