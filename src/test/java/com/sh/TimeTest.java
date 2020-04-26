package com.sh;

import com.sh.utils.DateTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
}
