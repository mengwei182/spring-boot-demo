package org.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RoleResourceRelation {
    // table column
    private String id;
    private String roleId;
    private String resourceId;
    private Date createTime;
    private Date updateTime;
}