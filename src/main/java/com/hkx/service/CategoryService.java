package com.hkx.service;

import com.hkx.entity.Category;
import com.hkx.entity.CategoryDTO;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    //查询一级类别
    HashMap<String, Object> queryOneCategoryByPage(Integer page, Integer rows);
    //查询二级类别分页
    HashMap<String, Object> querySecondCategoryByPage(Integer page, Integer rows,String parentId);
    //删除类别
    HashMap<String ,Object> deleteCategory(Category category);
    //面向接口编程
    List<CategoryDTO[]> queryAllCategory();
}
