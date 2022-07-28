package com.sh.dao;

import com.sh.entity.User;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

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

    void addList(List<User> list);

    void updateList(List<User> list);

    @MapKey("id")
    Map<Integer, String> getMap();

    List<Map<String, String>> listMap();
}
