package com.hkx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User1 {
    private String id;
    private String name;
    private int age;
    private Date bir;
}
