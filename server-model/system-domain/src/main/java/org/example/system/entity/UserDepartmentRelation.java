package org.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.BaseEntity;

/**
 * 用户部门信息表
 *
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("user_department_relation")
@EqualsAndHashCode(callSuper = true)
public class UserDepartmentRelation extends BaseEntity {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 部门id
     */
    private String departmentId;
}