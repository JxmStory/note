package com.sh.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 *  钉钉消息发送工具类
 *  @author micomo
 *  @date 2021/7/6 20:54
 */
public class DingTalkUtil {

    private static final Logger logger = LoggerFactory.getLogger(DingTalkUtil.class);

    private static final int READ_TIMEOUT = 20000;
    private static final int CONNECT_TIMEOUT = 2000;
    private static final String DING_SEND_URL = "https://oapi.dingtalk.com/robot/send?access_token=2cafc16ea5310d5130a520f3f37ac3be5ee461fb4d40e734b306339a3dd1e24d";

    public static void sendMessage(String title, String text, Boolean isAtAll, String[] atMobiles) {

        Map<String, Object> paramMap = new HashMap<>(2 << 3);
        // 设置消息格式
        paramMap.put("msgtype", "markdown");
        // 设置@人员
        Map<String, Object> at = new HashMap<>();
        at.put("isAtAll", isAtAll);
        at.put("atMobiles", atMobiles);
        paramMap.put("at", at);
        // 设置文本文案
        Map<String, Object> markdown = new HashMap<>();
        markdown.put("title", title);
        markdown.put("text", text);
        paramMap.put("markdown", markdown);
        
        try {
            String paramJson = JSONObject.toJSONString(paramMap);
            logger.info("【钉钉消息发送】, 请求参数={}", paramJson);
            // 设置header
            HttpHeaders headers = new HttpHeaders();
            headers.add("token", "");
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> requestEntity = new HttpEntity<String>(paramJson, headers);
            String result = getRestTemplate(READ_TIMEOUT, CONNECT_TIMEOUT).postForObject(DING_SEND_URL, requestEntity, String.class);
            logger.info("【钉钉消息发送】, 返回结果={}", result);
        } catch (Exception e) {
            logger.error("【钉钉消息发送】, 出现异常: {}", e.getMessage());
        }

    }

    public static RestTemplate getRestTemplate(int readTimeout, int connectTimeout) {
        RestTemplate rt = new RestTemplate();
        //设置超时时间
        ((SimpleClientHttpRequestFactory) rt.getRequestFactory()).setReadTimeout(readTimeout);
        ((SimpleClientHttpRequestFactory) rt.getRequestFactory()).setConnectTimeout(connectTimeout);
        return rt;
    }

    public static void main(String[] args) {

        String title = "Note-测试消息发送";
        String text =
                "#### 杭州天气 \n " +
                "> 9度，西北风1级，空气良89，相对温度73%\n " +
                "> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n " +
                "> ###### 10点20分发布 [天气](https://www.dingtalk.com) \n" +
                "> ##### @18338765039";
        Boolean isAtAll = false;
        String[] atMobiles = new String[]{"183****5039"};
        sendMessage(title, text,  isAtAll, atMobiles);
    }
}
