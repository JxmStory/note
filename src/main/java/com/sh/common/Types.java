package com.sh.common;

/**
 * @Description:常量枚举类
 * @Date: 2018-11-19 16:29
 */
public enum Types {

    CHQ_SSC("qc_ssc"),
    GD11X5("gd11x5"),
    JX11X5("jx11x5");

    private String type;

    Types(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
