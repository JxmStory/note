package com.sh.juc;

import com.sh.common.Result;
import com.sh.dao.UserDao;
import com.sh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Date: 2018-11-24 15:20
 * @Author: micomo
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    Lock lock = new ReentrantLock();


    /**
     * 调用方和被调用方属于同一个component时
     * 被调用方的 @Transacational注解无效
     */
    public synchronized Result update1() {
        Result result;
        try {
            // 事务不起作用
            result = update2();
        } finally {

        }
        return result;
    }


    @Transactional
    public Result update2() {
        User user = userDao.getMaster(1);
        /**
         *  第一次读取到数据库中初始用户名称
         */
        System.out.println(user.getUsername());
        try {
            // 在此时执行update2
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user = userDao.getMaster(1);
        /**
         * 在Mysql默认隔离级别（可重复读）下 同一事务中读取的内容是相同的
         * 可以理解为可重复读下 Mysql开启事务时操作的是一个快照 
         * 其他事务的修改删除对这个快照没有影响 但是其他事务插入的记录会同步到该快照中
         * 此时不管update3事务是否已提交 读取到的还是数据库中初始的用户名称
         * 
         * 若在Mysql-读已提交隔离级别下 同一事务中是会读取到其他事务已经提交的数据
         * 此时如果update3事务已经提交 读取到的是修改后的用户名称bbb 如未提交 则是初始数据
         *
         * 若在Mysql-读未提交隔离级别下 同一事务中可以读取到其他事务修改但是未提交的数据 当然已提交的数据也可以读取到
         * 此时不管update3事务是否提交 读取到的都是修改后的用户名称bbb
         *
         */ 
        System.out.println(user.getUsername());
        user.setUsername("aaa");
        userDao.update(user);
        return Result.success();
    }

    @Transactional
    public Result update3() {
        User user = userDao.getMaster(1);
        user.setUsername("bbb");
        userDao.update(user);
        return Result.success();
    }

}
