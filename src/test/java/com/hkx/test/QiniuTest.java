package com.hkx.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class QiniuTest {
    @Test
    public void testUpload() {
        //对于上传,公共空间和私有空间操作一样

        //构造一个带指定Region区域对象的配置类  参数：指定的区域  华北
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        String accessKey = "3jxU2dGm9q007vtEd-q1lcDokHbuMmO2cwzf9mFr"; //密钥：你的AK
        String secretKey = "kq0cpHMxbWdHuMlOcAdQATM1_cFG9fsssxfSRiX5"; //密钥：你的SK
        String bucket = "hkxvideo";  //存储空间的名字

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //String localFilePath = "C:\\Users\\NANAN\\Desktop\\video\\人民的名义.mp4";  //文件本地路径
        String localFilePath = "D:\\4.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "4.png";
        //根据密钥去做授权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传   普通文件上传
            Response response = uploadManager.put(localFilePath, key, upToken);
            //uploadManager.put(bytes, key, upToken)
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);  //文件名
            System.out.println(putRet.hash);  //文件内容的hash值
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

    }

    //公开空间下载
    @Test
    public void testDownLoad(){
        String fileName = "人民的名义.mp4";  //文件名,来自于数据库
        String domainOfBucket = "http://q5u1l78s3.bkt.clouddn.com";   //空间域名  图片的名字
        String finalUrl = String.format("%s/%s", domainOfBucket, fileName);
        System.out.println(finalUrl);  //http://q5u1l78s3.bkt.clouddn.com/人民的名义.mp4  网络路径

        String url="D://人民的名义sss.mp4";
        downloadFile(finalUrl,url);

    }

    //私有空间下载,也可以下载公共空间的内容
    @Test
    public void testDownLoads() throws UnsupportedEncodingException {

        String fileName = "人民的名义.mp4";  //文件名
        String domainOfBucket = "http://q5u15b459.bkt.clouddn.com";   //空间域名
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        String accessKey = "Z7UR6DLF1mNfZ--rbmJj0nm3tGA6xbI73eFoVNH1"; //密钥：你的AK
        String secretKey = "t1WvBo6vIw1NdjNPklPWY0e4SY5tY4ESu47NLchg"; //密钥：你的SK
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);

        String url="D://人民的名义aaa.mp4";
        downloadFile(finalUrl,url);
    }

    /**
     * 下载远程文件并保存到本地
     *
     * @param remoteFilePath-远程文件路径
     * @param localFilePath-本地文件路径（带文件名）
     * http://q5qobmi0y.bkt.clouddn.com/01.jpg
     */
    public void downloadFile(String remoteFilePath, String localFilePath) {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void delete(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String accessKey = "Z7UR6DLF1mNfZ--rbmJj0nm3tGA6xbI73eFoVNH1"; //密钥：你的AK
        String secretKey = "t1WvBo6vIw1NdjNPklPWY0e4SY5tY4ESu47NLchg"; //密钥：你的SK
        String bucket = "184-video";  //存储空间的名字
        String key = "人民的名义.mp4";
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
}
