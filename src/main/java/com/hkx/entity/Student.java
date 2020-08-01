package com.hkx.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value = "student")
public class Student {

    @Excel(name="ID")
    private String id;
    @Excel(name="学生姓名")
    private String name;
    @Excel(name="学生年龄")
    private Integer age;
}