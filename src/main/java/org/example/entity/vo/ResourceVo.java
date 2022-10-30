package org.example.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.BaseEntity;

/**
 * @author 李辉
 * @since 2022/10/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceVo extends BaseEntity {
    private String name;
    private String url;
    private String description;
    private String categoryId;
    private Integer status;
    private String categoryName;
}