package t.lt.user.biz.controller.dept;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.lt.common.pojo.CommonResult;
import t.lt.user.biz.controller.dept.vo.DeptCreateReqVO;
import t.lt.user.biz.controller.dept.vo.DeptListReqVO;
import t.lt.user.biz.controller.dept.vo.DeptRespVO;
import t.lt.user.biz.controller.dept.vo.DeptUpdateReqVO;
import t.lt.user.biz.convert.dept.DeptConvert;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.service.dept.DeptService;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static service.lt.common.pojo.CommonResult.success;


@Api(tags = "管理后台 - 部门")
@RestController
@RequestMapping("/system/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @PostMapping("create")
    @ApiOperation("创建部门")
    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    public CommonResult<Long> createDept(@Valid @RequestBody DeptCreateReqVO reqVO) {
        Long deptId = deptService.createDept(reqVO);
        return success(deptId);
    }

    @PutMapping("update")
    @ApiOperation("更新部门")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public CommonResult<Boolean> updateDept(@Valid @RequestBody DeptUpdateReqVO reqVO) {
        deptService.updateDept(reqVO);
        return success(true);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除部门")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    public CommonResult<Boolean> deleteDept(@RequestParam("id") Long id) {
        deptService.deleteDept(id);
        return success(true);
    }

    @GetMapping("/list")
    @ApiOperation("获取部门列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<List<DeptRespVO>> listDepts(DeptListReqVO reqVO) {
        List<DeptDO> list = deptService.getSimpleDepts(reqVO);
        list.sort(Comparator.comparing(DeptDO::getSort));
        return success(DeptConvert.INSTANCE.convertList(list));
    }



    @GetMapping("/get")
    @ApiOperation("获得部门信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<DeptRespVO> getDept(@RequestParam("id") Long id) {
        return success(DeptConvert.INSTANCE.convert(deptService.getDept(id)));
    }


}
