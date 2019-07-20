package com.sh.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * 功能描述: 自定义响应数据结构
 * @param:
 * @return:
 * @auther: admin
 */
public class Result<T> {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer code;// 返回结果 200成功，400请求参数错误，500服务器内部错误

    // 响应消息
    private String msg;//返回错误信息

    // 响应中的数据
    private T data;

    // 总条数
    private long count;

    // 响应时长
    private long spendTime;

    //构造函数
    public Result() {
        this.code = 200;
        this.msg = "";
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data) {
        this.code = 200;
        this.msg = "";
        this.data = data;
    }

    public Result(T data, long count) {
        this.code = 200;
        this.msg = "";
        this.data = data;
        this.count = count;
    }

    //响应成功（200,""）
    public static Result success() {
        return new Result();
    }

    //响应成功 返回对象(200,"",data)
    public static Result success(Object data) {
        return new Result(data);
    }

    //响应成功 返回集合(200,"",data,count)
    public static Result success(List<?> data, long count) {
        return new Result(data, count);
    }

    //响应失败(code,msg)
    public static Result fail(Integer code, String msg) {
        return new Result(code, msg);
    }

    private static Result build(int code, String msg, Object obj) {
        return new Result(code, msg, obj);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", count=" + count +
                ", spendTime=" + spendTime +
                '}';
    }
}
