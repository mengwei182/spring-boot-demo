package org.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.BaseEntity;
import org.example.mapper.MenuMapper;
import org.example.mapper.RoleMenuRelationMapper;
import org.example.result.CommonServerResult;
import org.example.result.SystemServerResult;
import org.example.result.exception.SystemException;
import org.example.service.MenuService;
import org.example.system.entity.Menu;
import org.example.system.entity.RoleMenuRelation;
import org.example.system.entity.vo.MenuVO;
import org.example.system.query.MenuQueryPage;
import org.example.util.CommonUtils;
import org.example.util.PageUtils;
import org.example.util.TreeModelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuRelationMapper roleMenuRelationMapper;

    /**
     * 新增菜单
     *
     * @param menuVo
     * @return
     */
    @Override
    public Boolean addMenu(MenuVO menuVo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVo, menu);
        menu.setId(CommonUtils.uuid());
        String parentId = BaseEntity.TOP_PARENT_ID;
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        // 有父级id
        if (!StrUtil.isEmpty(menuVo.getParentId())) {
            Menu parentMenu = menuMapper.selectOne(queryWrapper.eq(Menu::getId, menuVo.getParentId()));
            if (parentMenu == null) {
                throw new SystemException(SystemServerResult.PARENT_NOT_EXIST);
            }
            parentId = menuVo.getParentId();
            menu.setIdChain(parentMenu.getIdChain() + "," + parentMenu.getId());
        }
        // 无父级id
        if (StrUtil.isEmpty(menuVo.getParentId())) {
            menu.setParentId(BaseEntity.TOP_PARENT_ID);
            menu.setIdChain(BaseEntity.TOP_PARENT_ID);
        }
        Menu resultMenu = menuMapper.selectOne(queryWrapper.eq(Menu::getParentId, parentId).eq(Menu::getName, menuVo.getName()));
        if (resultMenu != null) {
            throw new SystemException(SystemServerResult.MENU_NAME_EXIST);
        }
        menuMapper.insert(menu);
        return true;
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteMenu(String id) {
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            throw new SystemException(CommonServerResult.OBJECT_NOT_EXIST);
        }
        menuMapper.deleteById(id);
        QueryWrapper<RoleMenuRelation> roleMenuRelationQueryWrapper = new QueryWrapper<>();
        roleMenuRelationQueryWrapper.lambda().eq(RoleMenuRelation::getMenuId, id);
        roleMenuRelationMapper.delete(roleMenuRelationQueryWrapper);
        return true;
    }

    /**
     * 更新菜单
     *
     * @param menuVo
     * @return
     */
    @Override
    public Boolean updateMenu(MenuVO menuVo) {
        Menu menu = menuMapper.selectById(menuVo.getId());
        if (menu == null) {
            throw new SystemException(CommonServerResult.OBJECT_NOT_EXIST);
        }
        BeanUtils.copyProperties(menuVo, menu);
        String parentId = BaseEntity.TOP_PARENT_ID;
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        // 有父级id
        if (!StrUtil.isEmpty(menuVo.getParentId())) {
            Menu parentMenu = menuMapper.selectOne(queryWrapper.eq(Menu::getId, menuVo.getParentId()));
            if (parentMenu == null) {
                throw new SystemException(SystemServerResult.PARENT_NOT_EXIST);
            }
            parentId = menuVo.getParentId();
            menu.setIdChain(parentMenu.getIdChain() + "," + parentMenu.getId());
        }
        // 无父级id
        if (StrUtil.isEmpty(menuVo.getParentId())) {
            menu.setParentId(BaseEntity.TOP_PARENT_ID);
            menu.setIdChain(BaseEntity.TOP_PARENT_ID);
        }
        Menu resultMenu = menuMapper.selectOne(queryWrapper.eq(Menu::getParentId, parentId).eq(Menu::getName, menuVo.getName()));
        if (resultMenu != null) {
            throw new SystemException(SystemServerResult.MENU_NAME_EXIST);
        }
        menuMapper.updateById(menu);
        return true;
    }

    /**
     * 查询菜单列表
     *
     * @param queryPage
     * @return
     */
    @Override
    public Page<MenuVO> getMenuList(MenuQueryPage queryPage) {
        Page<Menu> page = new Page<>(queryPage.getPageNumber(), queryPage.getPageSize());
        List<Menu> menuList = menuMapper.getMenuList(page, queryPage);
        page.setRecords(menuList);
        return PageUtils.wrap(page, MenuVO.class);
    }

    /**
     * 查询所有菜单列表
     *
     * @return
     */
    @Override
    public List<MenuVO> getAllMenuList() {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<>());
        return CommonUtils.transformList(menus, MenuVO.class);
    }

    /**
     * 查询菜单树列表
     *
     * @return
     */
    @Override
    public List<MenuVO> getMenuTreeList() {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<>());
        return TreeModelUtils.buildObjectTree(CommonUtils.transformList(menus, MenuVO.class));
    }
}