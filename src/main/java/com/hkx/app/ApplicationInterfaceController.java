package com.hkx.app;

import com.hkx.common.CommonResult;
import com.hkx.entity.VideoDTO;
import com.hkx.service.CategoryService;
import com.hkx.service.UserService;
import com.hkx.service.VideoService;
import com.hkx.util.AliyunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * App接口
 */
@RestController
@RequestMapping("app")
public class ApplicationInterfaceController {
    @Resource
    private UserService userService;
    @Resource
    VideoService videoService;
    @Resource
    CategoryService categoryService;
    @Autowired
    CommonResult commonResult;
    //@Autowired
    //private CommonResult commonResult;
    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCode(String phone){

        //发送手机验证码
        //实际上这些代码需要在service层实现,但是在service层中需要在后台也实现这些方法,所以在这里写

     /*   String random = AliyunUtil.getRandom(6);
        System.out.println(random);
        String message = AliyunUtil.getSendPhoneCode(phone, random);
        System.out.println(message);
        return new CommonResult().success("100","验证码发送成功",phone);*/


        //1.获取随机数
        String random = AliyunUtil.getRandom(6);
        //2.存储随机数     session    redis  key value 设置超时时间
        //3.给用户发送手机验证码
        String message = AliyunUtil.getSendPhoneCode(phone, random);
        System.out.println(message);
        return new CommonResult().success("100",message,phone);

    }
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(String phone){
        try {
            List<VideoDTO> videoDTOS = videoService.queryByReleaseTime();
            return commonResult.success(videoDTOS);
        }catch (Exception e){
            return commonResult.failed("请求失败");
        }
    }
    @RequestMapping("queryAllCategory")
    public  CommonResult queryAllCategory(){
        //categoryService.你好哈哈哈
        return  null;
    }





























}
