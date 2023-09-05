package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.MenuQueryPage;
import org.example.entity.system.vo.MenuVo;
import org.example.model.CommonResult;
import org.example.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 新增菜单
     *
     * @param menuVo
     * @return
     */
    @RequestMapping("/add")
    public CommonResult<Boolean> addMenu(@RequestBody MenuVo menuVo) {
        return CommonResult.success(menuService.addMenu(menuVo));
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public CommonResult<Boolean> deleteMenu(@RequestParam String id) {
        return CommonResult.success(menuService.deleteMenu(id));
    }

    /**
     * 更新菜单
     *
     * @param menuVo
     * @return
     */
    @RequestMapping("/update")
    public CommonResult<Boolean> updateMenu(@RequestParam MenuVo menuVo) {
        return CommonResult.success(menuService.updateMenu(menuVo));
    }

    /**
     * 查询菜单列表
     *
     * @param queryPage
     * @return
     */
    @RequestMapping("/list")
    public CommonResult<Page<MenuVo>> getMenuList(@ModelAttribute MenuQueryPage queryPage) {
        return CommonResult.success(menuService.getMenuList(queryPage));
    }

    /**
     * 查询所有菜单列表
     *
     * @return
     */
    @RequestMapping("/list/all")
    public CommonResult<List<MenuVo>> getAllMenuList() {
        return CommonResult.success(menuService.getAllMenuList());
    }

    /**
     * 查询所有菜单树
     *
     * @return
     */
    @RequestMapping("/list/tree")
    public CommonResult<List<MenuVo>> getMenuTreeList() {
        return CommonResult.success(menuService.getMenuTreeList());
    }
}