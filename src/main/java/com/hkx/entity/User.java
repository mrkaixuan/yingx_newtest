package com.hkx.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget(value = "user")

public class User {
    @Excel(name="ID")
    @Id
    private String id;
    @Excel(name="用户名")
    @Column(name = "username")
    private String username;

    @Excel(name="手机号")
    @Column(name = "phone")
    private String phone;

    @Excel(name="头像",type = 2 ,width = 40 , height = 20,imageType = 1)
    @Column(name = "pic_img")
    private String picImg;

    @Excel(name="签名")
    @Column
    private String sign;

    @Excel(name="状态")
    private String status;

    @Excel(name="微信号")
    private String wechat;

    @Excel(name="创建日期",format = "yyyy年MM月dd日",width = 20)
    @JsonFormat(pattern = "yyyy"+"年"+"MM"+"月"+"dd"+"日")//相应的JSON数据
    //@JSONField(format = "yyyy-MM-dd") //返回JSON数据
    //@DateTimeFormat(pattern = "yyyy-MM-dd")//接受请求的JSON数据
    @Column(name = "create_date")
    private Date createDate;

    @Excel(name="学分")
    private Double score;

    @Excel(name="城市")
    private String city;

    @Excel(name="性别")
    private String sex;

}