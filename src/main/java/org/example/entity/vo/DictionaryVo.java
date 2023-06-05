package org.example.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.BaseEntity;
import org.example.util.tree.TreeModelField;
import org.example.util.tree.TreeModelFieldEnum;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryVo extends BaseEntity {
    /**
     * 名称
     */
    @TreeModelField(field = TreeModelFieldEnum.NAME)
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 父级id
     */
    @TreeModelField(field = TreeModelFieldEnum.PARENT_ID)
    private String parentId;
    /**
     * id链
     */
    private String idChain;
}