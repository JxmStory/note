package com.sh.service.inter;

import com.sh.common.Result;
import com.sh.dbsource.DataSourceChoose;
import com.sh.entity.User;

import java.util.List;

/**
 * @Description:
 * @Date: 2018-11-24 15:18
 * @Author: micomo
 */
public interface UserServiceInter {

    // 自定义注解 values为所选数据库
    @DataSourceChoose(value = "master")
    Result getMaster(Integer id);

    @DataSourceChoose(value = "slave")
    Result getSlave(Integer id);

    // 不加注解使用默认数据库master
    Result update(User user);

    @DataSourceChoose(value = "slave")
    Result updateSlave(User user);

    @DataSourceChoose(value = "master")
    Result list();

    @DataSourceChoose(value = "master")
    List<User> findAll();

    Result add(User user);

    Result addList();

}
