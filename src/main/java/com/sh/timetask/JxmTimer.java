package com.sh.timetask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: micomo
 * @Date: 2019/1/11 10:06
 * @Description:
 */

@Component
public class JxmTimer {

//    @Scheduled(cron = "* 15 * * * ?")
    public void print(){
        System.out.println("--------------");
    }
}
