package com.hkx.service;

import com.hkx.dao.VideoMapper;
import com.hkx.entity.Video;
import com.hkx.entity.VideoDTO;
import com.hkx.entity.VideoExample;
import com.hkx.util.InterceptVideoPhotoUtil;
import com.hkx.util.QiniuUtil;
import com.hkx.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Resource
    VideoMapper videoMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //总条数   records
        VideoExample example = new VideoExample();
        Integer records = videoMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数  totals    总条数/每页展示条数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);

        //当前页  page
        map.put("page",page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoMapper.selectByRowBounds(new Video(), rowBounds);
        map.put("rows",videos);

        return map;
    }

    @Override
    public String add(Video video) {
        String uid = UUIDUtil.getUUID();

        //补充对象数据
        video.setId(uid);
        video.setUploadTime(new Date());

        //这里指定了各个id,实际上在前台根据操作的用户确定的
        video.setUserId("1");
        video.setCategoryId("1");
        video.setGroupId("1");

        videoMapper.insert(video);
        return uid;
    }



    @Override
    public void update(Video video) {
            System.out.println(video);
            videoMapper.updateByPrimaryKeySelective(video);//有选择性的进行修改
    }

    @Override
    public HashMap<String, Object> delete(Video video) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            //根据id查询视频数据
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(video.getId());
            Video videos = videoMapper.selectOneByExample(example);


            //1.删除数据库数据
            videoMapper.deleteByPrimaryKey(video);

            /*2.删除视频
             * 删除七牛云文件
             * 参数：
             *   bucket: 存储空间名
             *   fileName:  文件名   人民的名义.mp4
             * */
            //http://q5u1l78s3.bkt.clouddn.com/1581937007364-抖音视频.mp4
            String[] pathSplit = videos.getPath().split("/");
            QiniuUtil.deleteFile("hkxvideo",pathSplit[3]);

            /*
             * 3.删除封面
             * 删除七牛云文件
             * 参数：
             *   bucket: 存储空间名
             *   fileName:  文件名   人民的名义.mp4
             * */
            String[] coverSplit = videos.getCover().split("/");
            QiniuUtil.deleteFile("hkxvideo",coverSplit[3]);

            map.put("status","200");
            map.put("message","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","400");
            map.put("message","删除失败");
        }
        return map;

    }

    @Override
    public void uploadVdieo(MultipartFile path, String id, HttpServletRequest request) {

        //1.获取文件名
        String filename = path.getOriginalFilename();
        //文件名 + 时间戳区分-->新文件名
        String newName=new Date().getTime()+"-"+filename;
        //1581991049337-动画.mp4
        /*
         * 1.上传视频至七牛云
         * 参数：
         *   bucket: 存储空间名 七牛云的文件夹名
         *   file:   文件
         *   fileName:  文件名
         * */
        QiniuUtil.uploadFileQiniu("hkxvideo",path,newName);

        /**2.通过视频截取一张封面
         *
         * 获取指定视频的帧并保存为图片至指定目录
         * @param videofile  源视频文件路径
         * @param framefile  截取帧的图片存放路径
         * @throws Exception
         *
         * http://q5u1l78s3.bkt.clouddn.com/1581991049337-动画.mp4
         * D:\动画sss.jpg
         */
        //拼接远程视频路径
        String viodeFilePath="http://q5uqvk18d.bkt.clouddn.com/"+newName;

        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/cover");
        //判断文件夹是否存在，不存在创建文件夹
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //1581991049337-动画.mp4  newName
        String[] snames = newName.split("\\.");//获得文件的前缀
        String sName=snames[0];  //1581991049337-动画
        String photoName=sName+".jpg";  //拼接成图片名字   1581991049337-动画.jpg
        String photoPath=realPath+"\\"+photoName;  //频接图片报错的绝对路径

        //3.获取远端视频,截取视频图片到指定本地路径(upload-cover)
        try {
            InterceptVideoPhotoUtil.fetchFrame(viodeFilePath,photoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 4.视频截图上传-> 七牛云
         * 参数：
         *   bucket: 存储空间名
         *   filePath:   文件
         *   fileName:  文件名
         * */
        QiniuUtil.uploadFileQiniu("hkxvideo",photoPath,photoName);

        //5. 修改数据文件路径和封面
        Video video = new Video();
        video.setPath("http://q5uqvk18d.bkt.clouddn.com/"+newName);
        video.setCover("http://q5uqvk18d.bkt.clouddn.com/"+photoName);

        System.out.println("==video  update===="+video);
        //调用修改方法
        try {
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(id);
            videoMapper.updateByExampleSelective(video,example);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<VideoDTO> queryByReleaseTime() {
        List<VideoDTO> videoDTOS = videoMapper.queryByReleaseTime();
        return videoDTOS;
        //你好你好
    }
}
