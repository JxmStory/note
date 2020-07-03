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


    public synchronized Result update1() {

        try {
            update3();
        } finally {

        }
        return Result.success();
    }

    @Transactional
    public void update3() {
        User user = userDao.getMaster(1);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user.setUsername("aaa");
        userDao.update(user);
    }

    @Transactional
    public Result update2() {
        User user = userDao.getMaster(1);
        user.setUsername("bbb");
        userDao.update(user);
        return Result.success();
    }

}
