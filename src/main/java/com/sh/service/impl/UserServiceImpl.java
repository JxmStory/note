package com.sh.service.impl;

import com.alibaba.fastjson.JSON;
import com.sh.common.Assert;
import com.sh.common.MyException;
import com.sh.common.Result;
import com.sh.dao.UserDao;
import com.sh.entity.User;
import com.sh.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Date: 2018-11-24 15:20
 * @Author: micomo
 */
@Service
public class UserServiceImpl implements UserServiceInter {

    @Autowired
    private UserDao userDao;

    @Override
    public Result getMaster(Integer id) {
        // 使用断言判断id非空
        Assert.notNull(id, "id不能为空");
        Result result = new Result();
        User user = userDao.getMaster(id);
        if(null != user) {
            result = Result.success(user);
        } else {
            result = Result.fail(400, "找不到该用户");
        }
        return result;
    }

    @Override
    public Result getSlave(Integer id) {
        if(null == id)
            throw new MyException("id不能为空");
        Result result = new Result();
        User user = userDao.getSlave(id);
        if(null != user) {
            result = Result.success(user);
        } else {
            result = Result.fail(400, "找不到该用户");
        }
        return result;
    }

    @Override
    public Result update(User user) {
        Result result = new Result();
        Integer i = userDao.update(user);
        if(i>0) {
            result = Result.success();
        } else {
            result = Result.fail(500,"更新失败");
        }
        return result;
    }

    @Override
    public Result updateSlave(User user) {
        Result result = new Result();
        Integer i = userDao.update(user);
        if(i>0) {
            result = Result.success();
        } else {
            result = Result.fail(500,"更新失败");
        }
        return result;
    }

    @Override
    public Result list() {
//        int i = 1/0;
        Result result = new Result();
        List<User> list = userDao.list();
        if(list != null){
            result = Result.success(list);
        } else {
            result = Result.fail(500, "没有数据");
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> list = userDao.list();
        return list;
    }

    @Override
    public Result add(User user){
        if(user == null)
            throw new MyException("用户对象为空");
        userDao.add(user);
        return Result.success();
    }

    /**
     * 测试批量插入返回id
     * @return
     */
    @Override
    public Result addList() {
        List<User> list = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("a");
        user1.setAge(1);
        user1.setPassword("123");
        list.add(user1);
        User user2 = new User();
        user2.setUsername("a");
        user2.setAge(1);
        user2.setPassword("123");
        list.add(user2);
        User user3 = new User();
        user3.setUsername("a");
        user3.setAge(1);
        user3.setPassword("123");
        list.add(user3);
        userDao.addList(list);
        System.out.println(JSON.toJSONString(list));
        return Result.success();
    }

    @Override
    public Result updateList() {
        List<User> list = new ArrayList<>();

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("a");
        user1.setAge(1);
        user1.setPassword("123");
        list.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("a");
        user2.setAge(1);
        user2.setPassword("123");
        list.add(user2);

        User user3 = new User();
        user3.setId(3);
        user3.setUsername("a");
        user3.setAge(1);
        user3.setPassword("123");
        list.add(user3);

        userDao.updateList(list);
        System.out.println(JSON.toJSONString(list));
        return Result.success();
    }


}
