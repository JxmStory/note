package com.sh.thread;

import com.sh.common.Types;
import com.sh.entity.Lottery;
import com.sh.utils.DateTool;
import com.sh.utils.StringTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GrabThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(GrabThread.class);

    private String index;
    private String type;
    private Lottery lottery;

    public GrabThread(String index, String type) {
        this.index = index;
        this.type = type;
    }


    public void run(){
        int i = 0;
        while (i<12){
            i += 1;
            try{
                logger.info("{}开始第{}次抓取{}数据", Thread.currentThread().getName(), i, type);
                lottery = this.getLottery(); //抓取数据
                if(null != lottery){
                    logger.info("开奖数据 : {}", lottery.toString());
                    break;
                }else{
                    Thread.sleep(1000*30); //休眠10秒
                }
            }catch(Exception e){
                logger.info("发生异常 : {}", e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }


    private Lottery getLottery() throws IOException {

        if(Types.CHQ_SSC.getType().equals(type)){
            String url = "http://caipiao.163.com/award/cqssc/";
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByAttribute("data-win-number");
            Lottery cqssc = new Lottery();
            for(Element e : elements){
                if(index.equals(e.text())){
                    String code = e.attr("data-period");
                    String[] num = e.attr("data-win-number").split(" ");
                    cqssc.setTime(DateTool.getTime());
                    cqssc.setDate(DateTool.getDate());
                    cqssc.setType(Types.CHQ_SSC.getType());
                    cqssc.setCode(code);
                    cqssc.setIndex(index);
                    cqssc.setNum1(Integer.parseInt(num[0]));
                    cqssc.setNum2(Integer.parseInt(num[1]));
                    cqssc.setNum3(Integer.parseInt(num[2]));
                    cqssc.setNum4(Integer.parseInt(num[3]));
                    cqssc.setNum5(Integer.parseInt(num[4]));
                    logger.info("CHQ_SSC  code : {}  index : {}", code, index);
                    return cqssc;
                }
            }
        }

        if(Types.GD11X5.getType().equals(type)){
            String url = "http://caipiao.163.com/award/gd11xuan5/";
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByAttribute("data-period");
            Lottery gd11x5 = new Lottery();
            for(Element e : elements){
                if(index.equals(e.text())){
                    if(StringTool.isEmpty(e.attr("data-award"))){
                        return null;
                    } else {
                        String code = e.attr("data-period");
                        String[] num = e.attr("data-award").split(" ");
                        gd11x5.setTime(DateTool.getTime());
                        gd11x5.setDate(DateTool.getDate());
                        gd11x5.setType(Types.GD11X5.getType());
                        gd11x5.setCode(code);
                        gd11x5.setIndex(index);
                        gd11x5.setNum1(Integer.parseInt(num[0]));
                        gd11x5.setNum2(Integer.parseInt(num[1]));
                        gd11x5.setNum3(Integer.parseInt(num[2]));
                        gd11x5.setNum4(Integer.parseInt(num[3]));
                        gd11x5.setNum5(Integer.parseInt(num[4]));
                        logger.info("GD11X5  code : {}  index : {}", code, index);
                        return gd11x5;
                    }

                }
            }
        }


        if(Types.JX11X5.getType().equals(type)){
            String url = "http://caipiao.163.com/award/jx11xuan5/";
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByAttribute("data-period");
            Lottery jx11x5 = new Lottery();
            for(Element e : elements){
                if(index.equals(e.text())){
                    if(StringTool.isEmpty(e.attr("data-award"))){
                        return null;
                    } else {
                        String code = e.attr("data-period");
                        String[] num = e.attr("data-award").split(" ");
                        jx11x5.setTime(DateTool.getTime());
                        jx11x5.setDate(DateTool.getDate());
                        jx11x5.setType(Types.JX11X5.getType());
                        jx11x5.setCode(code);
                        jx11x5.setIndex(index);
                        jx11x5.setNum1(Integer.parseInt(num[0]));
                        jx11x5.setNum2(Integer.parseInt(num[1]));
                        jx11x5.setNum3(Integer.parseInt(num[2]));
                        jx11x5.setNum4(Integer.parseInt(num[3]));
                        jx11x5.setNum5(Integer.parseInt(num[4]));
                        logger.info("JX11X5  code : {}  index : {}", code, index);
                        return jx11x5;
                    }

                }
            }
        }
        return null;
    }

}
