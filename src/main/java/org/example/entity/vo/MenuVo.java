package org.example.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.BaseEntity;
import org.example.util.tree.TreeModelField;
import org.example.util.tree.TreeModelFieldEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuVo extends BaseEntity {
    /**
     * 名称
     */
    @NotNull(message = "名称不能为空")
    @TreeModelField(field = TreeModelFieldEnum.NAME)
    @Size(min = 1, max = 255, message = "名称应该在1-255字符之间")
    private String name;
    /**
     * 路由地址
     */
    @NotNull(message = "路由地址不能为空")
    @Size(min = 1, max = 255, message = "路由地址应该在1-255字符之间")
    private String path;
    /**
     * 组件
     */
    private String component;
    /**
     * 父级id
     */
    @TreeModelField(field = TreeModelFieldEnum.PARENT_ID)
    private String parentId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标
     */
    private String icon;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 0显示，1隐藏
     */
    private Integer hided;
    /**
     * 描述
     */
    private String description;
    /**
     * 子集
     */
    @TreeModelField(field = TreeModelFieldEnum.CHILDREN)
    private List<MenuVo> children;
}