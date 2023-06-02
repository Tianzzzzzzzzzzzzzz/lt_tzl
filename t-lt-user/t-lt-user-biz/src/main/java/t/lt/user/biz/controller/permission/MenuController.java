package t.lt.user.biz.controller.permission;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.lt.common.pojo.CommonResult;
import t.lt.user.biz.controller.permission.vo.menu.*;
import t.lt.user.biz.convert.permission.MenuConvert;
import t.lt.user.biz.dal.dataobject.permission.MenuDO;
import t.lt.user.biz.service.permission.MenuService;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static service.lt.common.pojo.CommonResult.success;


@Api(tags = "管理后台 - 菜单")
@RestController
@RequestMapping("/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/create")
    @ApiOperation("创建菜单")
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public CommonResult<Long> createMenu(@Valid @RequestBody MenuCreateReqVO reqVO) {
        Long menuId = menuService.createMenu(reqVO);
        return success(menuId);
    }

    @PutMapping("/update")
    @ApiOperation("修改菜单")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuUpdateReqVO reqVO) {
        menuService.updateMenu(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "角色编号", required= true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表", notes = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<List<MenuRespVO>> getMenus(MenuListReqVO reqVO) {
        List<MenuDO> list = menuService.getMenus(reqVO);
        list.sort(Comparator.comparing(MenuDO::getSort));
        return success(MenuConvert.INSTANCE.convertList(list));
    }



    @GetMapping("/get")
    @ApiOperation("获取菜单信息")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<MenuRespVO> getMenu(Long id) {
        MenuDO menu = menuService.getMenu(id);
        return success(MenuConvert.INSTANCE.convert(menu));
    }

}
