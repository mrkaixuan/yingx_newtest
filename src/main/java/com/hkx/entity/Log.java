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
@Table(name = "yx_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    private String id;
    @Column(name = "admin_name")
    private String adminName;
    @JsonFormat(pattern = "yyyy"+"年"+"MM"+"月"+"dd"+"日")//相应的JSON数据
    @DateTimeFormat(pattern = "yyyy"+"年"+"MM"+"月"+"dd"+"日")//相应的JSON数据
    @Column
    private Date time;
    @Column
    private String action;
    @Column
    private String status;
}