package org.example.system.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.BaseEntity;
import org.example.result.CommonServerResult;
import org.example.result.SystemServerResult;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceVO extends BaseEntity {
    /**
     * 资源名称
     */
    @NotNull(message = CommonServerResult.NAME_NOT_NULL)
    @Size(min = 1, max = 255, message = CommonServerResult.NAME_LENGTH_ERROR)
    private String name;
    /**
     * 资源URL
     */
    @NotNull(message = SystemServerResult.RESOURCE_URL_NOT_NULL)
    @Size(min = 1, max = 255, message = SystemServerResult.RESOURCE_URL_LENGTH_ERROR)
    private String url;
    /**
     * 描述
     */
    private String description;
    /**
     * 资源分类id
     */
    @NotNull(message = SystemServerResult.RESOURCE_CATEGORY_ID_NOT_NULL)
    @Size(message = SystemServerResult.RESOURCE_CATEGORY_ID_LENGTH_ERROR)
    private String categoryId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 资源分类名称
     */
    private String categoryName;
}