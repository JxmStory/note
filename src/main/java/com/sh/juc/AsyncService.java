package com.sh.juc;

import com.sh.utils.DateTool;
import com.sh.utils.DingTalkUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync // 开启异步调用
@Service
public class AsyncService {

    @Async("taskExecutor")
    public void sendMessage(String[] atMobiles) {
        Boolean isAtAll = false;
        StringBuffer at = new StringBuffer();
        if (atMobiles != null) {
            for (String phone : atMobiles) {
                at.append("@").append(phone);
            }
        }
        String title = "Note-测试消息发送";
        String time = DateTool.getTime("HH:mm");
        String text =
                "#### 北京天气 \n " +
                        "> 32度，西北风1级，空气良89，相对温度73%\n " +
                        "> ![screenshot](https://bkimg.cdn.bcebos.com/pic/8d5494eef01f3a29dca378d69425bc315c607cbb?x-bce-process=image/format,f_auto)\n " +
                        "> ###### " + time + "分发布 [详情](https://www.baidu.com) \n" +
                        "> ##### " + at.toString();
        DingTalkUtil.sendMessage(title, text,  isAtAll, atMobiles);
    }

}
