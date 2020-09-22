package com.sh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@RestController
@RequestMapping("/param/")
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
    @RequestMapping("map")
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

    @RequestMapping("user")
    public String user(@RequestBody Map<String, String> map) {
        return map.get("name");
    }

    /**
     *  文件方式上传
     *  @author micomo
     *  @date 2020/9/22 18:00
     */
    @RequestMapping(value = {"uploadFile"}, method = RequestMethod.POST)
    public void uploadFile(MultipartFile file) {
        try {
            //文件上传至指定目录
            File path = new File("D:/Work/Document/这周临时文件/temp/");
            if (!path.exists()) {
                path.mkdirs();
            }
            File image = new File(path + "/new.jpg");
            FileOutputStream fos = new FileOutputStream(image);
            InputStream in = file.getInputStream();
            int index = -1;
            byte[] buf = new byte[1024];
            while ((index = in.read(buf)) != -1) {
                fos.write(buf, 0, index);
            }
            fos.flush();
            in.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  二进制流方式上传
     *  @author micomo
     *  @date 2020/9/22 18:00
     */
    @RequestMapping(value = {"saveFileStream"}, method = RequestMethod.POST)
    public void saveFileStream(ServletRequest request) {
        try {
            //文件上传至指定目录
            File path = new File("D:/Work/Document/这周临时文件/temp/");
            if (!path.exists()) {
                path.mkdirs();
            }
            File image = new File(path + "/new.jpg");
            FileOutputStream fos = new FileOutputStream(image);
            InputStream in = request.getInputStream();
            int index = -1;
            byte[] buf = new byte[1024];
            while ((index = in.read(buf)) != -1) {
                fos.write(buf, 0, index);
            }
            fos.flush();
            in.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  二进制流上传文件到第三方
     *  @author micomo
     *  @date 2020/9/22 18:01
     */
    @RequestMapping(value = {"uploadFileStream"}, method = RequestMethod.POST)
    public void uploadFileStream() {
        String result = "";
        try {
            String url = "http://localhost:8081/param/saveFileStream";
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 发送请求参数
            DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
            File image = new File("D:/Work/Document/这周临时文件/temp/one.jpg");
            FileInputStream input = new FileInputStream(image);
            byte[] b = new byte[input.available()];
            input.read(b);
            input.close();

            dos.write(b);
            // flush输出流的缓冲
            dos.flush();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);//打印输出结果
        } catch (Exception e) {
            System.out.println("异常," + e.getMessage());
            e.printStackTrace();
        }
    }
}
