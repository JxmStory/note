package com.sh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("Param")
public class ParamController {

    /*
        {
            "corporateIdentity":"平台提供",
            "data":{
                "name":"jxmstory",
                "loanTerm":15
            },
            "sign":"签名规则参考 接口请求签名",
            "time":"1500693926"
        }
     */
    @RequestMapping("Map")
    public String getMap(@RequestBody Map<String, Object> map) {

//        JSONObject json = (JSONObject) map.get("data"); // 抛异常
//        String str = (String) map.get("data"); // 抛异常

        System.out.println(map.get("data").toString()); // {name=jxmstory, loanTerm=15}
        String jsonStr = JSON.toJSONString(map.get("data"));
        System.out.println(jsonStr); // {"name":"jxmstory","loanTerm":15}

        JSONObject data = JSON.parseObject(jsonStr);
        System.out.println(data.getString("name"));
        return "success";
    }
}
