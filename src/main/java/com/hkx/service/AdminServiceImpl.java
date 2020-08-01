package com.hkx.service;

import com.hkx.dao.AdminMapper;
import com.hkx.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Autowired
    private HttpSession session;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String,String> login(Admin admin,String enCode) {

        String imageCode = (String)session.getAttribute("imageCode");
        HashMap<String,String> map = new HashMap<>();

        //1.校验验证码
        if (imageCode.equals(enCode)){
            Admin admin1 = adminMapper.queryByUsername(admin.getUsername());
            if (admin1!=null){
                //用户存在 判断密码
                if (admin1.getPassword().equals(admin.getPassword())){
                    //登陆成功,存储管理员登陆标记
                    session.setAttribute("admin",admin);
                    map.put("status","200");
                    map.put("message","登陆成功");
                }else {
                    //密码错误
                    map.put("status","400");
                    map.put("message","密码错误");
                }
            }else {
                //用户不存在
                map.put("status","400");
                map.put("message","用户不存在");
            }
        }else {
            map.put("status","400");
            map.put("message","验证码错误");
        }
        return map;
    }
}
