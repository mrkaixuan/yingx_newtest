package com.hkx.test;

import com.hkx.util.AliyunUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliCloudTest {
    @Test
    public void testSendMsg() throws Exception {
        //前台传递过来  手机号
        String phoneNumbers="17337674";
        //1.获取随机数
        String random = AliyunUtil.getRandom(6);

        //2.存储随机数     session    redis  key value 设置超时时间
        //3.给用户发送手机验证码
        String sendPhoneCode = null;

        sendPhoneCode = AliyunUtil.getSendPhoneCode(phoneNumbers, random);

        System.out.println("短信发送："+sendPhoneCode);

    }
    @Test
    public void testSendMsgNew() {

    }
}
