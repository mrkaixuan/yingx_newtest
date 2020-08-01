package com.hkx.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User2 {

    @Excel(name="ID", height = 20, width = 30)
    private String id;
    @Excel(name="名字")
    private String name;
    @Excel(name="年龄")
    private Integer age;
    @Excel(name="生日",format = "yyyy年MM月dd日",width = 20)
    private Date bir;
}
