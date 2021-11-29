package org.example.controller;

import org.example.common.model.CommonResult;
import org.example.entity.ResourceCategory;
import org.example.service.ResourceCategoryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ResourceCategoryController {
    @Resource
    private ResourceCategoryService resourceCategoryService;

    @RequestMapping(value = "/resourceCategory", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CommonResult addResourceCategory(@RequestBody ResourceCategory resourceCategory) {
        return resourceCategoryService.addResourceCategory(resourceCategory);
    }

    @RequestMapping(value = "/resourceCategory", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateResourceCategory(@RequestBody ResourceCategory resourceCategory) {
        return resourceCategoryService.updateResourceCategory(resourceCategory);
    }
}