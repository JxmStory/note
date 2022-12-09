package com.sh.entity;

import java.io.Serializable;

/**
 * @Description:彩票
 * @Date: 2018-11-19 14:10
 */
public class Lottery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer num1;//1球
    private Integer num2;//2球
    private Integer num3;//
    private Integer num4;//
    private Integer num5;//
    private Integer num6;//
    private Integer num7;//
    private Integer num8;//
    private Integer num9;//
    private Integer num10;//
    private Integer num11;//
    private String code;//编号
    private String index;//序号
    private String date;//开奖日期
    private String time;//添加时间
    private String type;//彩票类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public Integer getNum3() {
        return num3;
    }

    public void setNum3(Integer num3) {
        this.num3 = num3;
    }

    public Integer getNum4() {
        return num4;
    }

    public void setNum4(Integer num4) {
        this.num4 = num4;
    }

    public Integer getNum5() {
        return num5;
    }

    public void setNum5(Integer num5) {
        this.num5 = num5;
    }

    public Integer getNum6() {
        return num6;
    }

    public void setNum6(Integer num6) {
        this.num6 = num6;
    }

    public Integer getNum7() {
        return num7;
    }

    public void setNum7(Integer num7) {
        this.num7 = num7;
    }

    public Integer getNum8() {
        return num8;
    }

    public void setNum8(Integer num8) {
        this.num8 = num8;
    }

    public Integer getNum9() {
        return num9;
    }

    public void setNum9(Integer num9) {
        this.num9 = num9;
    }

    public Integer getNum10() {
        return num10;
    }

    public void setNum10(Integer num10) {
        this.num10 = num10;
    }

    public Integer getNum11() {
        return num11;
    }

    public void setNum11(Integer num11) {
        this.num11 = num11;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", num1=" + num1 +
                ", num2=" + num2 +
                ", num3=" + num3 +
                ", num4=" + num4 +
                ", num5=" + num5 +
                ", num6=" + num6 +
                ", num7=" + num7 +
                ", num8=" + num8 +
                ", num9=" + num9 +
                ", num10=" + num10 +
                ", num11=" + num11 +
                ", code='" + code + '\'' +
                ", index='" + index + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("123");
    }
}
