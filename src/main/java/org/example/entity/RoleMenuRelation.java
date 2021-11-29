package org.example.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleMenuRelation {
    // table column
    private String id;
    private String roleId;
    private String menuId;
    private Date createTime;
    private Date updateTime;

    // not table column
    private List<String> menuIds;
}