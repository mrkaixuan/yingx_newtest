package com.hkx.dao;

import com.hkx.entity.Admin;


import tk.mybatis.mapper.common.Mapper;

public interface AdminMapper extends Mapper<Admin> {
   Admin queryByUsername(String username);
}