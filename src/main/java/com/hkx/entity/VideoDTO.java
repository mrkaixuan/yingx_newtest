package com.hkx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    //视频表
    private String id;
    private String videoTitle;
    private String description;
    private String cover;//封面路径
    private String path;//视频路径
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;//上传时间

    //点赞表
    private Integer likeCount;//点赞数量(需要查询点赞表)
    //类别表
    private String cateName;//所属二级类别名(需要查类别表)
    //用户表
    private String userPhoto;//视频所属的 用户头像

}
