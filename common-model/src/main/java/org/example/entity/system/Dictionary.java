package org.example.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.base.BaseEntity;
import org.example.util.tree.TreeModelField;
import org.example.util.tree.TreeModelFieldEnum;

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
    @TreeModelField(field = TreeModelFieldEnum.LABEL)
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