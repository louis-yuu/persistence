package com.yunbao.mapper;

import com.yunbao.pojo.User;

import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 上午10:47
 */
public interface UserMapper {


    User selectByPrimaryKey(Integer id);

    void save(User user);

    void update(User user);

    void deleteByPrimaryKey(Integer id);

    List<User> selectAll();


}
