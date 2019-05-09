package com.sh.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2018-11-24 15:00
 * @Auther: 季小沫的故事
 */
@ApiModel(value = "user", description = "用户对象")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", dataType = "Integer")
    private Integer id = 5;

    @ApiModelProperty(value = "用户名", dataType = "String")
    private String username;

    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    @ApiModelProperty(value = "年龄", dataType = "Integer")
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
