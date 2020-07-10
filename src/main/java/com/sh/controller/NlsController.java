package com.sh.controller;

import com.sh.common.MyException;
import com.sh.nls.SpeechRecognizerDemo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @Author: micomo
 * @Date: 2019/1/16 17:44
 * @Description:
 */
@RestController
public class NlsController {

    @RequestMapping(value = "/nls", method = RequestMethod.GET)
    public void nls(HttpServletResponse response){
        InputStream ins = SpeechRecognizerDemo.class.getResourceAsStream("/nls-sample-16k.wav");
        if (null == ins) throw new MyException("open the audio file failed!");
        new SpeechRecognizerDemo().process(ins, response);
    }
}
