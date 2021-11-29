package org.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ResourceCategory {
    // table column
    private String id;
    private String name;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
}