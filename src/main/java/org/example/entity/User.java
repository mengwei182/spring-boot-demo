package org.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    // table column
    private String id;
    private String username;
    private String password;
    private String icon;
    private String email;
    private String nickName;
    private String note;
    private Date loginTime;
    private Boolean enable;
    private Date createTime;
    private Date updateTime;

    // not table column
    private String phone;
}