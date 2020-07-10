package com.sh.entity;

import com.sh.excel.XlsField;

import java.io.Serializable;

/**
 * @Author: micomo
 * @Date: 2019/3/4 13:35
 * @Description:
 */
public class UserXls implements Serializable {

    private static final long serialVersionUID = 1L;

    @XlsField(name = "用户名")
    private String username;

    @XlsField(name = "密码")
    private String password;

    @XlsField(name = "年龄")
    private String age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserXls{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
