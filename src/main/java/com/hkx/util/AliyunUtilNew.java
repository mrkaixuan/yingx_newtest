package com.hkx.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;

public class AliyunUtilNew {
    public static CommonResponse SendCode(String signName,String templateCode,String phoneNumbers,String code) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FcJ5WXHXYEE5765ibDL", "oDbpsp9myl5nELdiYV7cFEbjVNaEDY");
        IAcsClient client = new DefaultAcsClient(profile);

        final String domin = "dysmsapi.aliyuncs.com";
        final String version = "2017-05-25";
        final String action = "SendSms";
        final String regionId = "cn-hangzhou";

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domin);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("RegionId",regionId);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{'code':"+code+"}");
        CommonResponse response = client.getCommonResponse(request);
        return response;

    }
    //生成随机数
    public static  String getRandom(int n){
        char[] code =  "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(code[new Random().nextInt(code.length)]);
        }
        return sb.toString();
    }

    public static String getSendPhoneCode(String phoneNumbers,String code){
        String signName="kaixuanApp";//签名
        String templateCode="SMS_184110545";  //模板id

        //调用发送验证码方法
        try {
            //响应对象
            CommonResponse commonResponse = SendCode(signName,templateCode,phoneNumbers,code);
            //响应Code
            String codeMsg = commonResponse.getData();
            System.out.println(codeMsg);
            String message=null;
            if(codeMsg.equals("OK")){
                message="发送成功";
            }else if (codeMsg.equals("isv.AMOUNT_NOT_ENOUGH")){
                message="账户余额不足";
            }
            return message;
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("出现异常，发送失败");
        }
    }
    public static void main(String[] args) {
        String phoneNumbers="17331459094";  //接收短信手机号
        //随机6位随机验证码
        String random = getRandom(6);
        System.out.println("验证码为:"+random);
        String message = getSendPhoneCode(phoneNumbers, random);
        System.out.println("发送结果"+message);
    }

}
