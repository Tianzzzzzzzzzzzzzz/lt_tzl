package t.lt.user.biz.controller.user;


import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import service.lt.common.pojo.CommonResult;
import service.lt.common.pojo.PageResult;
import t.lt.user.biz.controller.user.vo.UserCreateReqVO;
import t.lt.user.biz.controller.user.vo.UserPageItemRespVO;
import t.lt.user.biz.controller.user.vo.UserPageReqVO;
import t.lt.user.biz.controller.user.vo.UserUpdateReqVO;
import t.lt.user.biz.convert.user.UserConvert;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.dal.dataobject.permission.RoleDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;
import t.lt.user.biz.service.dept.DeptService;
import t.lt.user.biz.service.permission.RoleService;
import t.lt.user.biz.service.user.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static service.lt.common.pojo.CommonResult.success;
import static service.lt.common.util.collection.CollectionUtils.convertList;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private DeptService deptService;
    @Resource
    private RoleService roleService;
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "cms后台服务";
    }



    @PostMapping("/create")
    @ApiOperation("新增用户")
    @PreAuthorize("@ss.hasPermission('system:user:create')")
    public CommonResult<Long> createUser(@Valid @RequestBody UserCreateReqVO reqVO) {
        Long id = userService.createUser(reqVO);
        return success(id);
    }

    @PutMapping("update")
    @ApiOperation("修改用户")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody UserUpdateReqVO reqVO) {
        userService.updateUser(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return success(true);
    }
    @GetMapping("/page")
    @ApiOperation("获得用户分页列表")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public CommonResult<PageResult<UserPageItemRespVO>> getUserPage(@Valid UserPageReqVO reqVO) {
        // 获得用户分页列表
        PageResult<AdminUserDO> pageResult = userService.getUserPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal())); // 返回空
        }
        // 获得拼接需要的数据
        Collection<Long> deptIds = convertList(pageResult.getList(), AdminUserDO::getDeptId);
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
        Collection<Long> roleIds = convertList(pageResult.getList(), AdminUserDO::getRoleId);
        Map<Long, RoleDO> roleMap = roleService.getRoleMap(roleIds);
        // 拼接结果返回
        List<UserPageItemRespVO> userList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(user -> {
            UserPageItemRespVO respVO = UserConvert.INSTANCE.convert(user);
            respVO.setDeptNname(deptMap.get(user.getDeptId()).getDeptName());
            respVO.setRoleName(roleMap.get(user.getRoleId()).getRoleName());
            userList.add(respVO);
        });
        return success(new PageResult<>(userList, pageResult.getTotal()));
    }
    @DeleteMapping("/unable")
    @ApiOperation("禁用用户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:unable')")
    public CommonResult<Boolean> unableUser(@RequestParam("id") Long id) {
        userService.unableUser(id);
        return success(true);
    }

    @DeleteMapping("/enable")
    @ApiOperation("启用用户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:enable')")
    public CommonResult<Boolean> enableUser(@RequestParam("id") Long id) {
        userService.enableUser(id);
        return success(true);
    }

}
