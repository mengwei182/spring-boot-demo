package org.example.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.BaseEntity;
import org.example.tree.TreeModelField;
import org.example.tree.TreeModelFieldEnum;

/**
 * 菜单信息表
 *
 * @author lihui
 * @since 2022/10/29
 */
@Data
@TableName("menu")
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {
    /**
     * 名称
     */
    @TreeModelField(TreeModelFieldEnum.LABEL)
    private String name;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 组件
     */
    private String component;
    /**
     * 父级id
     */
    @TreeModelField(TreeModelFieldEnum.PARENT_ID)
    private String parentId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * id链
     */
    private String idChain;
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
}