package org.example.controller;

import org.example.api.MenuQueryPage;
import org.example.common.model.CommonResult;
import org.example.entity.Menu;
import org.example.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class MenuController {
    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/menu", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CommonResult addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
    public CommonResult getMenu(@RequestParam String id) {
        return menuService.deleteMenu(id);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateMenu(@RequestParam Menu menu) {
        return menuService.updateMenu(menu);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public CommonResult getMenu(@ModelAttribute MenuQueryPage queryPage) {
        return menuService.getMenuList(queryPage);
    }
}