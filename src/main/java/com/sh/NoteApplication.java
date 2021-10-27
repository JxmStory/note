package com.sh;

import com.sh.nls.SpeechRecognizerDemo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableScheduling // 开启定时任务
@MapperScan(basePackages = {"com.sh.dao"}) // dao类无需再加@Mapper
@ServletComponentScan(basePackages = {"com.sh.filter"}) // 扫描过滤器
@EnableSwagger2
public class NoteApplication {

	public static void main(String[] args) {
		// 创建语音识别client 避免调用语音识别时重复创建
		SpeechRecognizerDemo.createClient();
		SpringApplication.run(NoteApplication.class, args);
	}
}
