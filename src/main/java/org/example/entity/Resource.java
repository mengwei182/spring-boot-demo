package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("resource")
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseEntity {
    private String name;
    private String url;
    private String description;
    private String categoryId;
    private Integer status;
}