package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.MenuQueryPage;
import org.example.entity.Menu;
import org.example.entity.RoleMenuRelation;
import org.example.entity.vo.MenuVo;
import org.example.error.CommonErrorResult;
import org.example.error.exception.CommonException;
import org.example.mapper.MenuMapper;
import org.example.mapper.RoleMenuRelationMapper;
import org.example.service.MenuService;
import org.example.util.CommonUtils;
import org.example.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public Boolean addMenu(MenuVo menuVo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVo, menu);
        menu.setId(CommonUtils.uuid());
        menuMapper.insert(menu);
        return true;
    }

    @Override
    public Page<MenuVo> getMenuList(MenuQueryPage queryPage) {
        Page<Menu> page = new Page<>(queryPage.getPageNumber(), queryPage.getPageSize());
        List<Menu> menuList = menuMapper.getMenuList(page, queryPage);
        page.setRecords(menuList);
        return PageUtils.wrap(page, MenuVo.class);
    }

    @Override
    @Transactional
    public Boolean deleteMenu(String id) {
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            throw new CommonException(CommonErrorResult.OBJECT_NOT_EXIST);
        }
        menuMapper.deleteById(id);
        QueryWrapper<RoleMenuRelation> roleMenuRelationQueryWrapper = new QueryWrapper<>();
        roleMenuRelationQueryWrapper.lambda().eq(RoleMenuRelation::getMenuId, id);
        roleMenuRelationMapper.delete(roleMenuRelationQueryWrapper);
        return true;
    }

    @Override
    public Boolean updateMenu(MenuVo menuVo) {
        Menu menu = menuMapper.selectById(menuVo.getId());
        if (menu == null) {
            throw new CommonException(CommonErrorResult.OBJECT_NOT_EXIST);
        }
        BeanUtils.copyProperties(menuVo, menu);
        menuMapper.updateById(menu);
        return true;
    }
}