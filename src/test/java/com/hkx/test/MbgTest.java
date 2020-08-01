package com.hkx.test;

import com.hkx.dao.AdminMapper;
import com.hkx.entity.Admin;
import com.hkx.entity.AdminExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MbgTest {
    @Resource
    AdminMapper adminMapper;


    @Test
    /**
     * insert两个方法.效果一样
     */
    public void insert() {

        //adminMapper.insert(new Admin());//这样会报错,id不能为null
        //adminMapper.insert(new Admin("4", null, "33"));
        //adminMapper.insertSelective(new Admin("5", null, "33"));
    }

    @Test
    /**
     * 两个方法,基本一样
     */
    public void delete() {
        AdminExample example = new AdminExample();
        example.createCriteria().andIdEqualTo("5");
       // adminMapper.deleteByExample(example);
        //adminMapper.deleteByPrimaryKey("6");

    }

    @Test
    public void update() {
        //全部修改,如果没有传值,会传空值
        //adminMapper.updateByPrimaryKey(new Admin("4", "xiaokeai", null));
        //部分修改,如果没传值,不会修改
        //adminMapper.updateByPrimaryKeySelective(new Admin("4", null, "xiaokeai"));
        //创建条件对象
        AdminExample example = new AdminExample();
        example.createCriteria().andIdEqualTo("3");
        //adminMapper.updateByExample(new Admin("6","dakeai","dakeai"),example);

    }

    @Test
    public void select() {
//        AdminExample example = new AdminExample();
//        example.createCriteria().andIdEqualTo("4");
//        List<Admin> admins = adminMapper.selectByExample(example);//根据example查询得到集合
//        List<Admin> admins1 = adminMapper.selectByExample(new AdminExample());//查询所有


        Admin admin = adminMapper.selectByPrimaryKey("4");//返回对象
        System.out.println(admin);
        //Admin admin = adminMapper.queryByUsername("11");
        //System.out.println(admin);


    }

    @Test
    public void test(){
        String a = "http://qkljkls/ksdjgl.mp4";
        String[] split = a.split("/");

        ArrayList<String> objects = new ArrayList<>();
        for (String s : split) {
            objects.add(s);
        }

        System.out.println(objects.get(3));
    }
}

