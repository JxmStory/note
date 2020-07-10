package com.sh.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: jxmStory
 * @Date: 2019/1/11 11:13
 * @Description: 全局异常处理
 * 捕捉controller中的异常 根据不同异常类型进行处理
 * 处理完成后直接向前端响应结果
 */
@ControllerAdvice
public class GlobalException {

    @Value("${note.printExp}")
    private boolean printExp;

    /**
     * 全局异常捕捉处理
     * @param e
     * @return map
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result allExceptionHandler(Exception e) {
        if (printExp) e.printStackTrace();
        String message = e.getMessage();
        if (e.getCause()!=null) {
            String[] msg = e.getCause().getMessage().split("content:");
            if (msg!=null&&msg.length>=2) {
                message = msg[1];
            }
        }
        return Result.fail(500, message);
    }

    /**
     * 拦截捕捉自定义异常 MyException
     * @param e
     * @return map
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Result myExceptionHandler(MyException e) {
        if (printExp) e.printStackTrace();
        return Result.fail(500, e.getMsg());
    }
}
