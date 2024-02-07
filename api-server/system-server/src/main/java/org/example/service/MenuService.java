package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.query.MenuQueryPage;
import org.example.entity.system.vo.MenuVO;

import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface MenuService {
    /**
     * 新增菜单
     *
     * @param menuVo
     * @return
     */
    Boolean addMenu(MenuVO menuVo);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    Boolean deleteMenu(String id);

    /**
     * 更新菜单
     *
     * @param menuVo
     * @return
     */
    Boolean updateMenu(MenuVO menuVo);

    /**
     * 查询菜单列表
     *
     * @param queryPage
     * @return
     */
    Page<MenuVO> getMenuList(MenuQueryPage queryPage);

    /**
     * 查询所有菜单列表
     *
     * @return
     */
    List<MenuVO> getAllMenuList();

    /**
     * 查询菜单树列表
     *
     * @return
     */
    List<MenuVO> getMenuTreeList();
}