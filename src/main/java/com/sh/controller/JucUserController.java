package com.sh.controller;

import com.sh.common.Result;
import com.sh.juc.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date: 2018-11-24 15:41
 * @Author: micomo
 */
@RestController
@RequestMapping("/api/juc")
@CrossOrigin("*")
@Api(value = "事务多数据源测试", tags = { "事务多数据源测试Api文档" })
public class JucUserController {

    @Autowired
    private UserService jucUserService;

    @GetMapping("/addUserAge1")
    public Result addUserAge1() {
        jucUserService.addUserAge1();
        return Result.success();
    }

    @GetMapping("/addUserAge2")
    public Result addUserAge2() {
        jucUserService.addUserAge2();
        return Result.success();
    }

    @GetMapping("/addUserAge3")
    public Result addUserAge3() {
        jucUserService.addUserAge3();
        return Result.success();
    }

    @GetMapping("/update1")
    public Result update1() {
        return jucUserService.update1();
    }

    /**
     *  先调用update2 再调用update3
     *
     *  整个事务过程： 假设数据库用户名称为abc （2、3顺序无所谓）
     *
     *  1.update2开启事务读取到用户名称abc
     *  2.update2开始休眠（模拟事务处理中）
     *  3.update3开启事务读取到用户名称abc
     *  4.update3修改用户名称为bbb并提交事务 （此时数据库里用户名称为bbb）
     *  5.update2结束休眠，再次读取到的用户名称是abc （读取的是快照中的）
     *  6.update2修改用户名称为aaa并提交事务 （此时数据库里用户名称为aaa）
     *
     */
    @GetMapping("/update2")
    public Result update2() {
        return jucUserService.update2();
    }

    @GetMapping("/update3")
    public Result update3() {
        return jucUserService.update3();
    }

    // TODO: 2020/12/27  
    @GetMapping("/getUserManyDataSource")
    public Result getUserManyDataSource() {
        return jucUserService.getUserManyDataSource();
    }

}
