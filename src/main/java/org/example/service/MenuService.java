package org.example.service;

import org.example.api.MenuQueryPage;
import org.example.common.model.CommonResult;
import org.example.entity.Menu;

public interface MenuService {
    CommonResult addMenu(Menu menu);

    CommonResult getMenuList(MenuQueryPage queryPage);

    CommonResult deleteMenu(String id);

    CommonResult updateMenu(Menu menu);
}