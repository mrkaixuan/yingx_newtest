package com.hkx.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value = "student1")
public class Student1 {

    @Excel(name="ID")
    private String id;
    @Excel(name="学生姓名")
    private String name;
    @Excel(name="学生年龄")
    private Integer age;
    @Excel(name="头像",type = 2 ,width = 40 , height = 20,imageType = 1)
    private String headPic;
    //表示type =2 该字段类型为图片,imageType=1 (默认可以不填),表示从file读取,字段类型是个字符串类型 可以用相对路径也可以用绝对路径,绝对路径优先依次获取
}
