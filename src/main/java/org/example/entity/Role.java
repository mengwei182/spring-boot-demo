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
    // 名称
    private String name;
    // 父级id
    private String parentId;
    // id链
    private String idChain;
    // 级别
    private Integer level;
    // 排序
    private Integer sort;
    // 图标
    private String icon;
    // 状态
    private Integer status;
    // 描述
    private String description;
}