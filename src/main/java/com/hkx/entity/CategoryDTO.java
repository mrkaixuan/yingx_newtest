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
public class CategoryDTO {
    //视频表
    private String id;
    private String cateName;
    private String levels;
    private String parentId;//父类id
    private CategoryDTO[] categoryDTOS;//视频路径
}
