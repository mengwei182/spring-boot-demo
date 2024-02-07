package org.example.entity.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.base.BaseEntity;

/**
 * 角色资源关联表
 *
 * @author lihui
 * @since 2022/10/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleResourceRelationVO extends BaseEntity {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 资源id
     */
    private String resourceId;
}