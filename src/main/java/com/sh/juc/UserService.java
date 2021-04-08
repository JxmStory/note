package com.sh.juc;

import com.sh.common.Result;
import com.sh.dao.UserDao;
import com.sh.dbsource.TransactionAppoint;
import com.sh.entity.User;
import com.sh.service.inter.UserServiceInter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserServiceInter userServiceInter;

    /**
     *  以下三个方法 在@Transactional注解开启下 锁都会失效
     *
     *  事务开启时 Spring会生成代理类来执行相应方法
     *  方法执行完时 同步代码块（方法）上的锁会被释放
     *  如果此时代理类还未提交事务 其他线程进入到同步代码块（方法）中
     *  因此产生并发问题
     *
     */
//    @Transactional
    public synchronized void addUserAge1() {
        User user = userDao.getMaster(1);
        user.setAge(user.getAge() + 1);
        userDao.update(user);
    }

    @Transactional
    public void addUserAge2() {
        synchronized(this){
            User user = userDao.getMaster(1);
            user.setAge(user.getAge() + 1);
            userDao.update(user);
        }
    }

    Lock lock = new ReentrantLock();
    @Transactional
    public void addUserAge3() {
        lock.lock();
        try {
            User user = userDao.getMaster(1);
            user.setAge(user.getAge() + 1);
            userDao.update(user);
        } finally {
            lock.unlock();
        }
    }

    /**
     *  正确做法
     *  加锁的方法或代码块要包含完整的被调用的带事务方法
     *  注意：此方法不能与被调用的带事务方法在一个@Service或着component下
     */
    public synchronized void addUserAge() {
        addUserAge1();
        // 或者
        synchronized(this) {
            addUserAge1();
        }
        // 或者
        lock.lock();
        try {
            addUserAge1();
        } finally {
            lock.unlock();
        }
    }


    /**
     * 调用方和被调用方属于同一个component时
     * 被调用方的 @Transacational注解无效
     * 原因：spring 在扫描bean的时候会扫描方法上是否包含@Transactional注解
     *      如果有，spring会生成代理类 代理类方法在被调用时就会启动transaction
     *      如果没有，该方法会由bean直接调用 不通过代理类 因此不会启动transaction
     *
     */
    public Result update1() {
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


    /**
     *  多数据源下事务测试
     *  使用默认事务管理器 会导致只修改成功主库的数据
     *  使用自定义注解事务管理器 可以同步修改主从数据库数据
     *  @author micomo
     *  @date 2020/12/27 15:32
     */
//    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    @TransactionAppoint(value = {"masterTransaction", "slaveTransaction"})
    public Result getUserManyDataSource() {
        Result result = new Result();
        User user = userDao.getMaster(1);
        user.setUsername("abcdeft");
        result = userServiceInter.update(user);
        logger.info("【多数据源下事务测试】 result={}", result.toString());
        result = userServiceInter.updateSlave(user);
        logger.info("【多数据源下事务测试】 result={}", result.toString());
        return result;
    }


}
