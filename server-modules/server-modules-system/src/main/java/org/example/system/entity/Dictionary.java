package org.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.core.domain.BaseEntity;
import org.example.common.core.tree.TreeModelField;
import org.example.common.core.tree.TreeModelFieldEnum;

/**
 * 字典信息表
 *
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("dictionary")
@EqualsAndHashCode(callSuper = true)
public class Dictionary extends BaseEntity {
    /**
     * 名称
     */
    @TreeModelField(TreeModelFieldEnum.LABEL)
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 父级id
     */
    @TreeModelField(TreeModelFieldEnum.PARENT_ID)
    private Long parentId;
    /**
     * id链
     */
    private String idChain;
}