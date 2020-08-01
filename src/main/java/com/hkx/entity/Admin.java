package com.hkx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "yx_admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    private String id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Date registDate;

}