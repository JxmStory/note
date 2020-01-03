package com.sh.common;

/**
 * @Author: micomo
 * @Date: 2019/3/13 18:31
 * @Description:
 */
public enum Color {
    RED("红色"), YELLOW("黄色");

    private String value;

    private Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(String code) {
        return Color.valueOf(code).value;
    }

    public static Color getColor(String code) {
        return Color.valueOf(code);
    }
}
