package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
    private String name;
    private String parentId;
    private Integer level;
    private Integer sort;
    private String icon;
    private Integer status;
    private String description;
}