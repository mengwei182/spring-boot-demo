package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("resource_category")
@EqualsAndHashCode(callSuper = true)
public class ResourceCategory extends BaseEntity {
    private String name;
    private Integer sort;
    private String description;
}