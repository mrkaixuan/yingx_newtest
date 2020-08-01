package com.hkx.controller;

import com.google.gson.Gson;
import com.hkx.entity.User;
import com.hkx.service.UserService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 数据增删改查
     * @param oper
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public String edit(String oper, User user,String id){
        String userId = null;

        if (oper.equals("add")){
            userId = userService.addUser(user);
        }
        if (oper.equals("edit")){
            userService.update(user);
        }
        if (oper.equals("del")){
            userService.delete(user);
        }
        return  userId;

    }

    /**
     * 文件上传
     * @param picImg
     * @param id
     * @param session
     */
    //注意userId是从edit中添加方法后得到的,通过ajax传过去后又穿回来进行文件上传的数据完善(修改路径,默认为fakepath)
    //注意此处传递过来的文件名必须和Jqgrid中写的一致,否则报空指针异常
    @ResponseBody
    @RequestMapping("uploadUserPicImg")
    public void uploadUserPicImg(MultipartFile picImg, String id, HttpSession session){

        //---文件上传
        //1.根据相对路径获取绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/cover");

        //如果文件夹不存在,先创建文件夹
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        //2.获取文件名(添加时间戳用于区分文件)
        String filename = new Date().getTime()+"-"+picImg.getOriginalFilename();
        //3.文件上传
        try {
            picImg.transferTo(new File(realPath,filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //---修改数据通过id
        User user = new User();
        user.setId(id);
        user.setPicImg(filename);

        //调用修改方法
        userService.update(user);
    }


    /**
     * 分页展示
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("queryByPage")
    public HashMap<String, Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = userService.queryByPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("uploadUserPicImgQiniu")
    public void uploadUserPicImgQiniu(MultipartFile picImg, String id, HttpServletRequest request){

        //1.向七牛云提交文件  提交到

        String filename = picImg.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;

        //将文件转为字节数组,用字节数组上传
        byte[] bytes=null;
        try {
            bytes = picImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构造一个带指定Region对象的配置类  参数：指定的区域  华北
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        String accessKey = "3jxU2dGm9q007vtEd-q1lcDokHbuMmO2cwzf9mFr"; //密钥：你的AK
        String secretKey = "kq0cpHMxbWdHuMlOcAdQATM1_cFG9fsssxfSRiX5"; //密钥：你的SK
        String bucket = "hkxvideo";  //存储空间的名字

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //String localFilePath = "C:\\Users\\NANAN\\Desktop\\video\\人民的名义.mp4";  //文件本地路径
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = newName;
        //根据密钥去做授权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传   文件上传  文件字节数组
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);  //文件名
            //System.out.println(putRet.hash);  //件内容的hash值
            //http://q5u1l78s3.bkt.clouddn.com/照片.jpg  网络路径
            //http://q5u1l78s3.bkt.clouddn.com/人民的名义.mp4

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        //1.修改数据
        //B修改数据
        User user = new User();
        user.setId(id);
        user.setPicImg("http://q5uqvk18d.bkt.clouddn.com/"+newName);

        //调用修改方法
        userService.update(user);


    }
    @ResponseBody
    @RequestMapping("getPhone")
    public HashMap<String, Object> getPhoneCode(String phone){
        HashMap<String, Object> map = userService.getPhoneCode(phone);
        return map;
    }


    @ResponseBody
    @RequestMapping("exportExcel")
    public HashMap<String, Object> exportExcel(){
        HashMap<String, Object> map = userService.exportExcel();
        return map;
    }

}
