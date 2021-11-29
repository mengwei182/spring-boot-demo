package org.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Resource {
    // table column
    private String id;
    private String name;
    private String url;
    private String description;
    private String categoryId;
    private Boolean enable;
    private Date createTime;
    private Date updateTime;

    // not table column
    private String categoryName;
}