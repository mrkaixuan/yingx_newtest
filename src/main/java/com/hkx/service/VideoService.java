package com.hkx.service;

import com.hkx.entity.Video;
import com.hkx.entity.VideoDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface VideoService {
    /**
     * 查询视频内容(分页展示)
     * @param page
     * @param rows
     * @return
     */
    HashMap<String, Object> queryByPage(Integer page,Integer rows);

    /**
     * 添加视频,负责食品的入库
     * @param video
     * @return 返回该视频的id,通过这个id进行文件上传
     */
    String add(Video video);

    /**
     * 更新视频
     * @param video
     */
    void update(Video video);

    /**
     * 删除视频
     * @param video
     * @return
     */
    HashMap<String, Object> delete(Video video);

    /**
     * 上传视频
     * @param path  path 是上传的文件对象
     * @param id 改id是视频信息入口后 add方法返回的id
     * @param request
     */
    void uploadVdieo(MultipartFile path, String id, HttpServletRequest request);


    /**
     * 接口: 查询视频以及关联信息,返回给前台页面
     * @return
     */
    List<VideoDTO> queryByReleaseTime();
}
