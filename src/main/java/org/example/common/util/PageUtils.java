package org.example.common.util;

import org.example.common.model.Page;
import org.example.common.model.QueryPage;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

public class PageUtils {
    private PageUtils() {
    }

    public static Page wrapper(QueryPage queryPage, Collection<?> list, Integer total) {
        Page page = new Page();
        BeanUtils.copyProperties(queryPage, page);
        page.setTotal(total == null ? 0 : total);
        page.setData(list);
        return page;
    }
}