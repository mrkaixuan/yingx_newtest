package com.hkx.service;

import com.hkx.entity.City;
import com.hkx.entity.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    /**
     * 添加方法
     * @param user
     * @return
     */
    String addUser(User user);

    /**
     * 更新方法
     * @param user
     */
    void update(User user);

    /**
     * 分页方法,通过jqgrid分页,传递过来数据进行处理
     * @param page
     * @param rows
     * @return 返回一个map集合,把jqgrid需要的4个数据通过json返回
     */
    HashMap<String, Object> queryByPage(Integer page, Integer rows);


    /**
     * 删除用户操作
     * @param user
     * @return
     */
    HashMap<String, Object> delete(User user);

    /**
     * 发送验证码,并且返回验证码
     * @param phone
     * @return
     */
    HashMap<String, Object> getPhoneCode(String phone);

    /**
     * 导出用户为excel表
     * @return
     */
    HashMap<String, Object> exportExcel();

    List<City> queryMessage(String sex);

    List<City> queryMonth(String sex);

}
