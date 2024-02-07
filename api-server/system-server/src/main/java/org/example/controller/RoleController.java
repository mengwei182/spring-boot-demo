package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.query.RoleQueryPage;
import org.example.entity.system.vo.RoleMenuRelationVO;
import org.example.entity.system.vo.RoleVO;
import org.example.model.CommonResult;
import org.example.service.RoleService;
import org.springframework.web.bind.annotation.*;

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