package org.example.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserRoleRelation {
    // table column
    private String id;
    private String userId;
    private String roleId;
    private Date createTime;
    private Date updateTime;

    // not table column
    private List<String> roleIds;
}