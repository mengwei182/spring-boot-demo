package org.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Role {
    // table column
    private String id;
    private String name;
    private String description;
    private Boolean enable;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
}