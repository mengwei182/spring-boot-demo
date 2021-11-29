package org.example.vo;

import lombok.Data;

@Data
public class UsernamePasswordVo {
    private String id;
    private String username;
    private String phone;
    private String password;
    private String phoneVerifyCode;
    private String imageVerifyCode;
}