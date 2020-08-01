package com.hkx.dao;

import com.hkx.entity.Category;
import com.hkx.entity.CategoryDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {
    List<CategoryDTO[]> queryAllCategory();
}