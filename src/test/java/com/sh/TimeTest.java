package com.sh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sh.utils.DateTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeTest {

    @Test
    public void timeTest() throws Exception {

        String time = DateTool.getTime();
        System.out.println(time);
        Date date = DateTool.formatString(time, "yyyy-MM-dd hh:mm:ss");
        String timeStr = DateTool.formatDate(date, "yyyy-MM-dd");
        System.out.println(timeStr);
    }

    @Test
    public void dateTest() {
        Date date = new Date();
        Result result = new Result();
        result.setName("hhh");
        result.setData(date);
        Date now = (Date) result.getData();
        System.out.println(now);

    }

    class Result {
        private String name;
        private Object data;
        String getName() {
            return this.name;
        }
        void setName(String name) {
            this.name = name;
        }
        Object getData() {
            return this.data;
        }
        void setData(Object data) {
            this.data = data;
        }
    }

    @Test
    public void JsonTest() {
        JSONObject json = new JSONObject();
        json.put("name", "songhuihui");
        double amount = 12.00;
        json.put("amount", amount);
        json.put("date", "2018-2-02");
        System.out.println(json.toJSONString());
        System.out.println(JSON.toJSONString(json));
        Map<String, Object> map = new HashMap<>();
        map.put("json", json);
        System.out.println(JSON.toJSONString(map.get("json")));
    }
}
