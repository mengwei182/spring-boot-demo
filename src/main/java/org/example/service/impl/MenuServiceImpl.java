package org.example.service.impl;

import org.example.api.MenuQueryPage;
import org.example.common.model.CommonResult;
import org.example.common.model.Page;
import org.example.common.util.PageUtils;
import org.example.entity.Menu;
import org.example.mapper.MenuMapper;
import org.example.mapper.RoleMenuRelationMapper;
import org.example.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public CommonResult addMenu(Menu menu) {
        menuMapper.addMenu(menu);
        return CommonResult.success();
    }

    @Override
    public CommonResult getMenuList(MenuQueryPage queryPage) {
        List<Menu> menuList = menuMapper.getMenuList(queryPage);
        Integer total = menuMapper.getMenuListCount(queryPage);
        Page page = PageUtils.wrapper(queryPage, menuList, total);
        return CommonResult.success(page);
    }

    @Override
    public CommonResult deleteMenu(String id) {
        menuMapper.deleteMenu(id);
        roleMenuRelationMapper.deleteRoleMenuRelationByMenuId(id);
        return CommonResult.success();
    }

    @Override
    public CommonResult updateMenu(Menu menu) {
        menuMapper.updateMenu(menu);
        return CommonResult.success();
    }
}