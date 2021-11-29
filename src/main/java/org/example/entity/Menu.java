package org.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Menu {
    // table column
    private String id;
    private String parentId;
    private String title;
    private Integer level;
    private Integer sort;
    private String name;
    private String icon;
    private Boolean hidden;
    private Date createTime;
    private Date updateTime;
}