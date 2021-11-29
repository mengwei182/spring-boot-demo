package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.api.MenuQueryPage;
import org.example.entity.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {
    void addMenu(Menu menu);

    void deleteMenu(String id);

    void updateMenu(Menu menu);

    List<Menu> getMenuList(MenuQueryPage queryPage);

    Integer getMenuListCount(MenuQueryPage queryPage);

    List<Menu> getMenusByUserId(String userId);
}