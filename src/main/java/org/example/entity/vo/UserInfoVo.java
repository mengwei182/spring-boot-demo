package org.example.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.BaseEntity;
import org.example.entity.Menu;
import org.example.entity.Role;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author 李辉
 * @since 2022/10/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoVo extends BaseEntity {
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    private String icon;
    private String phone;
    private String email;
    private String name;
    private String description;
    private Date loginTime;
    private Integer status;
    private List<Role> roles;
    private List<Menu> menus;
}