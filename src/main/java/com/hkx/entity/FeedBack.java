package com.hkx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public   class FeedBack {

    @Id
    private String id;

    @Column
    private String title;

    @Column
    private String content;

    @JsonFormat(pattern = "yyyy"+"年"+"MM"+"月"+"dd"+"日")//相应的JSON数据
    @Column(name = "feedback_time")
    private Date feedbackTime;

    @Column(name = "user_id")
    private String userId;
}