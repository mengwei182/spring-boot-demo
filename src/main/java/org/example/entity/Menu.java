package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("menu")
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {
    private String name;
    private String parentId;
    private String idChain;
    private Integer level;
    private Integer sort;
    private String icon;
    private Integer status;
    private String description;
}