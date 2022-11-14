package org.example.controller;

import org.example.api.MenuQueryPage;
import org.example.model.CommonResult;
import org.example.entity.vo.MenuVo;
import org.example.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @RequestMapping("/add")
    public CommonResult addMenu(@RequestBody MenuVo menuVo) {
        return CommonResult.success(menuService.addMenu(menuVo));
    }

    @RequestMapping("/delete")
    public CommonResult deleteMenu(@RequestParam String id) {
        return CommonResult.success(menuService.deleteMenu(id));
    }

    @RequestMapping("/update")
    public CommonResult updateMenu(@RequestParam MenuVo menuVo) {
        return CommonResult.success(menuService.updateMenu(menuVo));
    }

    @RequestMapping("/list")
    public CommonResult getMenuList(@ModelAttribute MenuQueryPage queryPage) {
        return CommonResult.success(menuService.getMenuList(queryPage));
    }
}