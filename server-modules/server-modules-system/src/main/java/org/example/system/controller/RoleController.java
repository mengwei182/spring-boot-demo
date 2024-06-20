package org.example.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.core.result.CommonResult;
import org.example.system.entity.vo.RoleMenuRelationVO;
import org.example.system.entity.vo.RoleVO;
import org.example.system.entity.query.RoleQueryPage;
import org.example.system.service.RoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
     * 新增角色
     *
     * @param roleVo
     * @return
     */
    @ApiOperation("新增角色")
    @PostMapping("/add")
    public CommonResult<Boolean> addRole(@RequestBody RoleVO roleVo) {
        return CommonResult.success(roleService.addRole(roleVo));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/delete")
    public CommonResult<Boolean> deleteRole(@RequestParam String id) {
        return CommonResult.success(roleService.deleteRole(id));
    }

    /**
     * 更新角色
     *
     * @param roleVo
     * @return
     */
    @ApiOperation("更新角色")
    @PutMapping("/update")
    public CommonResult<Boolean> updateRole(@RequestBody RoleVO roleVo) {
        return CommonResult.success(roleService.updateRole(roleVo));
    }

    /**
     * 新增角色菜单关系
     *
     * @param roleMenuRelationVo
     * @return
     */
    @ApiOperation("新增角色菜单关系")
    @PostMapping("/menu/update")
    public CommonResult<Boolean> addRoleMenu(@RequestBody RoleMenuRelationVO roleMenuRelationVo) {
        return CommonResult.success(roleService.addRoleMenu(roleMenuRelationVo));
    }

    /**
     * 查询角色列表
     *
     * @param queryPage
     * @return
     */
    @ApiOperation("查询角色列表")
    @GetMapping("/list")
    public CommonResult<Page<RoleVO>> getRoleList(@ModelAttribute RoleQueryPage queryPage) {
        return CommonResult.success(roleService.getRoleList(queryPage));
    }

    /**
     * 查询所有角色列表
     *
     * @return
     */
    @ApiOperation("查询所有角色列表")
    @GetMapping("/list/all")
    public CommonResult<List<RoleVO>> getAllRoleList() {
        return CommonResult.success(roleService.getAllRoleList());
    }
}