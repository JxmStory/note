package com.sh.dao;

import com.sh.entity.User;

import java.util.List;

/**
 * @Description:
 * @Date: 2018-11-24 15:17
 * @Author: micomo
 */
public interface UserDao {

    Integer update(User user);

    User getMaster(Integer id);

    User getSlave(Integer id);

    List list();

    void add(User user);
}
