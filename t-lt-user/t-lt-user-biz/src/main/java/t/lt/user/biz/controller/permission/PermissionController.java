package t.lt.user.biz.controller.permission;

import cn.hutool.core.collection.CollUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.lt.common.pojo.CommonResult;
import t.lt.user.biz.controller.permission.vo.permission.PermissionAssignRoleDataScopeReqVO;
import t.lt.user.biz.controller.permission.vo.permission.PermissionAssignRoleMenuReqVO;
import t.lt.user.biz.controller.permission.vo.permission.PermissionAssignUserRoleReqVO;
import t.lt.user.biz.service.permission.PermissionService;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

import static service.lt.common.pojo.CommonResult.success;

/**
 * 权限 Controller，提供赋予用户、角色的权限的 API 接口
 *
 * @author 芋道源码
 */
@Api(tags = "管理后台 - 权限")
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;


    @ApiOperation("获得角色拥有的菜单编号")
    @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-role-resources")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Set<Long>> listRoleMenus(Long roleId) {
        return success(permissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/assign-role-menu")
    @ApiOperation("赋予角色菜单")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAssignRoleMenuReqVO reqVO) {

        // 执行菜单的分配
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    @PostMapping("/assign-role-data-scope")
    @ApiOperation("赋予角色数据权限")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-data-scope')")
    public CommonResult<Boolean> assignRoleDataScope(@Valid @RequestBody PermissionAssignRoleDataScopeReqVO reqVO) {
        permissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return success(true);
    }

    @ApiOperation("获得管理员拥有的角色编号列表")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-user-roles")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return success(permissionService.getUserRoleIdListByUserId(userId));
    }

    @ApiOperation("赋予用户角色")
    @PostMapping("/assign-user-role")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserRole(@Validated @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }

}
