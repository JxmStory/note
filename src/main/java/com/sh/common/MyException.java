package com.sh.common;

/**
 * @Description: 自定义异常
 * @Date: 2018-11-24 11:41
 * @Author: micomo
 */
public class MyException extends RuntimeException {

    private String code;
    private String msg;

    public MyException(String msg) {
        super(msg);
        this.code = "500";
        this.msg = msg;
    }

    public MyException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
