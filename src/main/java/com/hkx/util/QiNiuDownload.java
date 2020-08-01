package com.hkx.util;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;

public class QiNiuDownload {


    private static String accessKey = "Z7UR6DLF1mNfZ--rbmJj0nm3tGA6xbI73eFoVNH1"; //密钥：你的AK
    private static String secretKey = "t1WvBo6vIw1NdjNPklPWY0e4SY5tY4ESu47NLchg"; //密钥：你的SK



    public static void delete(String myKey){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

        String bucket = "184-video";  //存储空间的名字
        //key = "人民的名义.mp4";
        String key = myKey;

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    public static void uploadFile(){

    }
}
