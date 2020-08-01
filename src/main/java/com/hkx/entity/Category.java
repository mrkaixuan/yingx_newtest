package com.hkx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "yx_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String levels;
    @Column(name = "parent_id")
    private String parentId;

}