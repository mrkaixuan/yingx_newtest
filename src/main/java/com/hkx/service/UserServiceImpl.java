package com.hkx.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.hkx.annotation.AddLog;
import com.hkx.dao.UserMapper;
import com.hkx.entity.City;
import com.hkx.entity.User;
import com.hkx.entity.UserExample;
import com.hkx.util.AliyunUtil;
import com.hkx.util.UUIDUtil;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @AddLog(value="添加用户")
    @Override
    public String addUser(User user) {
        //补充4个数据
        //id
        String userId = UUIDUtil.getUUID();
        user.setId(userId);
        //status
        user.setStatus("1");
        //date
        user.setCreateDate(new Date());
        //score
        user.setScore(0.0);

        //数据库入库
        userMapper.insert(user);


        //通过Goeasy发送给管道,从而更新Echarts表格数据
        HashMap<String, Object> map = new HashMap<>();

        List<City> man = userMapper.queryMonth("男");
        List<City> woman = userMapper.queryMonth("女");

        ArrayList<String> month = new ArrayList<>();
        ArrayList<String> boys = new ArrayList<>();
        ArrayList<String> girls = new ArrayList<>();

        for (int i=1;i<=12;i++){
            month.add(i+"月");
            int num =0;
            for (City city : man) {
                if(city.getName().equals(i+"")){
                    num++;
                    boys.add(city.getValue()+"");
                }
            }
            if (num==0){
                boys.add(0+"");
            }
        }
        for (int i=1;i<=12;i++){
            int num =0;
            for (City city : woman) {
                if(city.getName().equals(i+"")){
                    num++;
                    girls.add(city.getValue()+"");
                }
            }
            if (num==0){
                girls.add(0+"");
            }
        }
//        System.out.println(man);
//        System.out.println(woman);
//        System.out.println(month);
        map.put("month", month);
        map.put("boys", boys);
        map.put("girls", girls);

        //将对象转为json格式字符串
        String content = JSON.toJSONString(map);
        //配置GoEasy参数：redionHost:应用的地址,Appkey：你的appKey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-df0a45499f274b2bae29ae50a6a12dc9");
        //发送消息
        goEasy.publish("My184Channel", content);

        //返回用户id用于文件上传
        return userId;

    }
    @AddLog(value="更新用户信息")
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        /**
         * 传递数据
         * page 当前页
         * rows 每页展示数据
         *
         * 返回数据
         * total 总页数
         * records 总条数
         * page 当前页
         * rows 数据
         */
        HashMap<String, Object> map = new HashMap<>();

        //总条数
        int records = userMapper.selectCountByExample(new UserExample());
        //总页数 totals = 总条数/每页展示数据
        Integer totals = records%rows==0?records/rows:records/rows+1;
        //当前页page
        //数据 rows 分页 使用RowBounds 从第几条开始,需要几条数据
        List<User> users = userMapper.selectByRowBounds(new User(), new RowBounds((page - 1) * rows, rows));

        map.put("totals",totals);
        map.put("records",records);
        map.put("page",page);
        map.put("rows",users);
        return map;

    }
    @AddLog(value="删除用户")
    @Override
    public HashMap<String, Object> delete(User user) {
        HashMap<String, Object> map = new HashMap<>();

        userMapper.deleteByPrimaryKey(user);
        map.put("status","200");
        map.put("message","删除用户成功");
        return map;
    }

    @Override
    public HashMap<String, Object> getPhoneCode(String phone) {
        HashMap<String, Object> map = new HashMap<>();
        String message = null;
        try {
            //1.获取随机数
            String random = AliyunUtil.getRandom(6);
            //2.存储随机数     session    redis  key value 设置超时时间
            //3.给用户发送手机验证码
            message = AliyunUtil.getSendPhoneCode(phone, random);
            map.put("status", "200");
            map.put("message", "发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("message", "发送失败：" + message);
        }
        return map;
    }

    @Override
    public HashMap<String, Object> exportExcel() {
        HashMap<String, Object> map = new HashMap<>();
        String message = null;
        try {
            //获得User集合
            List<User> users = userMapper.selectAll();
            System.out.println(users);
            for (User user : users) {
                user.setPicImg("src/main/webapp/upload/cover/"+user.getPicImg());
            }
            //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户表"), User.class,users);

            workbook.write(new FileOutputStream(new File("F://用户信息表.xls")));
            //释放资源
            workbook.close();

            map.put("status", "200");
            map.put("message", "Excel文档导出成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("message", "Excel文档导出失败：" + message);
        }
        return map;
    }

    @Override
    public List<City> queryMessage(String sex) {
        return  userMapper.queryMessage(sex);
    }

    @Override
    public List<City> queryMonth(String sex) {
        return  userMapper.queryMonth(sex);
    }
}
