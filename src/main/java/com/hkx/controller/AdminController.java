package com.hkx.controller;

import com.hkx.entity.Admin;
import com.hkx.service.AdminService;
import com.hkx.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;


@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("getImageCode")
    //获取验证码方法
    public void getImageCode(HttpSession session, HttpServletResponse response){
        //1.获得随机字符
        String securityCode = ImageCodeUtil.getSecurityCode();
        //2.存储随机字符
        //System.out.println("===="+securityCode);
        session.setAttribute("imageCode",securityCode);
        //3.生成图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        //4.将生成的验证码图片以png(1.png)的格式输出到D盘        "D:\\1.png"   ==  "D:/1.png"
        try {
            ImageIO.write(image, "png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,String> login(Admin admin,String enCode){
        HashMap<String, String> map = adminService.login(admin, enCode);
        return map;
    }
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}
