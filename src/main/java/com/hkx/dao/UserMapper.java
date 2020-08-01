package com.hkx.dao;

import com.hkx.entity.City;
import com.hkx.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<City> queryMessage(String sex);
    List<City> queryMonth(String sex);
}