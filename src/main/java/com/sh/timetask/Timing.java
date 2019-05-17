package com.sh.timetask;

import com.sh.common.Types;
import com.sh.thread.GrabThread;
import com.sh.utils.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class Timing {

    private static final Logger logger = LoggerFactory.getLogger(Timing.class);

//    @Scheduled(cron = "0 02,12,22,32,42,52 10-22 * * ?")
    public void openCqssc() throws ParseException {

        int minute = DateTool.getMinute("10:00", DateTool.getTime().substring(11,16));
        String index = String.format("%03d", minute/10+24);

        logger.info("=====>>>>>{} 第 {} 期 ", Types.CHQ_SSC.getType(), index);
        GrabThread grabThread = new GrabThread(index, Types.CHQ_SSC.getType());
        Thread thread = new Thread(grabThread);
        thread.start();
    }

//    @Scheduled(cron = "20 02,12,22,32,42,52 9-23 * * ?")
    public void openGdx() throws ParseException {

        int minute = DateTool.getMinute("09:00", DateTool.getTime().substring(11,16));
        String index = String.format("%02d", minute/10);

        logger.info("=====>>>>>{} 第 {} 期 ", Types.GD11X5.getType(), index);
        GrabThread grabThread = new GrabThread(index, Types.GD11X5.getType());
        Thread thread = new Thread(grabThread);
        thread.start();
    }

//    @Scheduled(cron = "40 02,12,22,32,42,52 9-23 * * ?")
    public void openJxx() throws ParseException {

        int minute = DateTool.getMinute("09:00", DateTool.getTime().substring(11,16));
        String index = String.format("%02d", minute/10);

        logger.info("=====>>>>>{} 第 {} 期 ", Types.GD11X5.getType(), index);
        GrabThread grabThread = new GrabThread(index, Types.JX11X5.getType());
        Thread thread = new Thread(grabThread);
        thread.start();
    }

}
