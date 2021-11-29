package org.example.vo;

import lombok.Data;
import org.example.entity.Menu;
import org.example.entity.Role;

import java.util.List;

@Data
public class UserInfoVo {
    private String id;
    private String username;
    private String icon;
    private String email;
    private String nickName;
    private String note;
    private Boolean enable;
    private List<Role> roles;
    private List<Menu> menus;
}