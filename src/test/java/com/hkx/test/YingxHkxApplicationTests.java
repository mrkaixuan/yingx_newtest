package com.hkx.test;

import com.hkx.dao.AdminMapper;
import com.hkx.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxHkxApplicationTests {

    @Resource
    AdminMapper adminMapper;
    @Test
    public void contextLoads() {
        Admin admin = adminMapper.queryByUsername("1");
        System.out.println(admin);
    }
}
