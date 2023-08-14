package org.example.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.base.BaseEntity;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String icon;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 姓名
     */
    private String name;
    /**
     * 部门ID
     */
    private String departmentId;
    /**
     * 描述
     */
    private String description;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 状态：0禁用，1正常
     */
    private Integer status;
}