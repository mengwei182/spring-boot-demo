package org.example.controller;

import org.example.common.model.CommonResult;
import org.example.entity.Role;
import org.example.entity.RoleMenuRelation;
import org.example.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class RoleController {
    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/role", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CommonResult addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    @RequestMapping(value = "/role", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
    public CommonResult deleteRole(@RequestParam String id) {
        return roleService.deleteRole(id);
    }

    @RequestMapping(value = "/role", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @RequestMapping(value = "/menu/update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateRoleMenu(@RequestBody RoleMenuRelation roleMenuRelation) {
        return roleService.updateRoleMenu(roleMenuRelation);
    }
}