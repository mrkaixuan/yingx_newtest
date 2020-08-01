package com.hkx.controller;

import com.hkx.entity.Category;
import com.hkx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

//@RestController 如果写成这样,就表明内部的方法全部返回Json数据,这样方法就不必写@ResponseBody了
@RestController
//@Controller
@RequestMapping("cate")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    /**
     * 分页展示
     * @param page
     * @param rows
     * @return
     */
    //@ResponseBody
    @RequestMapping("queryOneCategoryByPage")
    public HashMap<String, Object> queryOneCategoryByPage(Integer page, Integer rows){
        HashMap<String, Object> map = categoryService.queryOneCategoryByPage(page,rows);
        return map;
    }


    //@ResponseBody
    @RequestMapping("querySecondCategoryByPage")
    public HashMap<String, Object> querySecondCategoryByPage(Integer page, Integer rows,String parentId){
        HashMap<String, Object> map = categoryService.querySecondCategoryByPage(page,rows,parentId);
        return map;
    }


    /**
     * 完成表单的增删改查操作
     * @param category
     * @param oper
     * @return
     */
    @RequestMapping("edit")
    public Object edit(Category category, String oper){

        //1.数据入库   返回数据id
        //2.添加之后  做文件上传
        //3.修改文件路径
        System.out.println("==category=="+category);
        String id=null;
        if(oper.equals("add")){
            //id = categoryService.add(category);
            return id;
        }

        if(oper.equals("edit")){}

        if(oper.equals("del")){
            HashMap<String, Object> map = categoryService.deleteCategory(category);
            return map;
        }
        return "";
    }



}
