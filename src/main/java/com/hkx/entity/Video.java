package com.hkx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private String id;

    private String title;

    private String brief;

    private String cover;

    private String path;

    @JsonFormat(pattern = "yyyy"+"年"+"MM"+"月"+"dd"+"日")//相应的JSON数据
    @DateTimeFormat(pattern = "yyyy"+"年"+"MM"+"月"+"dd"+"日")//相应的JSON数据
    @Column(name = "upload_time")
    private Date uploadTime;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "group_id")
    private String groupId;

}