package t.lt.user.biz.controller.permission;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.lt.common.pojo.CommonResult;
import service.lt.common.pojo.PageResult;
import t.lt.user.biz.controller.permission.vo.menu.*;
import t.lt.user.biz.convert.permission.MenuConvert;
import t.lt.user.biz.dal.dataobject.permission.LogDO;
import t.lt.user.biz.dal.dataobject.permission.MenuDO;
import t.lt.user.biz.service.permission.LogService;
import t.lt.user.biz.service.permission.MenuService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static service.lt.common.pojo.CommonResult.success;


@Api(tags = "管理后台 - 日志")
@RestController
@RequestMapping("/log")
@Validated
public class LogController {

    @Resource
    private LogService logService;

    /**
     * 日志列表
     * @param
     * @author
     * @date
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取菜单列表", notes = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('system:log:query')")
    public CommonResult<PageResult<LogDO>> getMenus(LogReqVO reqVO) {
        PageResult<LogDO> pageResult = logService.getLogPage(reqVO);
        return success(pageResult);
    }

    /**
     * 导出日志
     * @param
     * @author
     * @date
     */
    @PostMapping("/export")
    @PreAuthorize("@ss.hasPermission('system:log:export')")
    public void exportLogExcel(@Valid @RequestBody HttpServletResponse response, LogReqVO reqVO)  throws IOException {

        logService.exportLogExcel(response,reqVO);

    }


    @DeleteMapping("/delete")
    @ApiOperation("删除日志")
    @ApiImplicitParam(name = "ids", value = "日志id数组", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:log:delete')")
    public CommonResult<Boolean> deleteRole(@RequestParam("ids") Collection<Long> ids) {
        logService.deletelogByIds(ids);
        return success(true);
    }


}
