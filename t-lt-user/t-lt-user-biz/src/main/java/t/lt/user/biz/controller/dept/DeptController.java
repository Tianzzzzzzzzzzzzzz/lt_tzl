package t.lt.user.biz.controller.dept;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.lt.common.pojo.CommonResult;
import service.lt.common.pojo.PageResult;
import t.lt.user.biz.controller.dept.vo.*;
import t.lt.user.biz.controller.user.vo.UserPageItemRespVO;
import t.lt.user.biz.convert.dept.DeptConvert;
import t.lt.user.biz.convert.user.UserConvert;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.dal.dataobject.permission.RoleDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;
import t.lt.user.biz.service.dept.DeptService;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static service.lt.common.pojo.CommonResult.success;
import static service.lt.common.util.collection.CollectionUtils.convertList;


@Api(tags = "管理后台 - 机构")
@RestController
@RequestMapping("/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @PostMapping("create")
    @ApiOperation("创建机构")
    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    public CommonResult<Long> createDept(@Valid @RequestBody DeptCreateReqVO reqVO) {
        Long deptId = deptService.createDept(reqVO);
        return success(deptId);
    }

    @PutMapping("update")
    @ApiOperation("更新机构")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public CommonResult<Boolean> updateDept(@Valid @RequestBody DeptUpdateReqVO reqVO) {
        deptService.updateDept(reqVO);
        return success(true);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除机构")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    public CommonResult<Boolean> deleteDept(@RequestParam("id") Long id) {
        deptService.deleteDept(id);
        return success(true);
    }

    @GetMapping("/list")
    @ApiOperation("获取机构列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<PageResult<DeptRespVO>> listDepts(DeptListReqVO reqVO) {
        // 获得机构分页列表
        PageResult<DeptDO> pageResult = deptService.getDeptPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal())); // 返回空
        }
        // 获得拼接需要的数据
        Collection<Long> pids = convertList(pageResult.getList(), DeptDO::getPid);
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(pids);

        // 拼接结果返回
        List<DeptRespVO> deptRespVOList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(dept -> {
            DeptRespVO respVO = DeptConvert.INSTANCE.convert(dept);
            respVO.setPName(deptMap.get(dept.getPid()).getDeptName());
            deptRespVOList.add(respVO);
        });
        return success(new PageResult<>(deptRespVOList, pageResult.getTotal()));
    }


    @GetMapping("/tree")
    @ApiOperation("获取机构树")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<List<DeptTreeRespVO>> listTree(DeptListReqVO reqVO) {
        List<DeptTreeRespVO> resultList=new ArrayList<>();
        //先模糊查询
        List<DeptDO> deptDOList=deptService.getSimpleDepts(reqVO);
        //筛选1级机构
        List<DeptDO> firstDeptList=deptDOList.stream().filter(e->e.getPid().equals(0l)).collect(Collectors.toList());
        List<Long> pids=firstDeptList.stream().map(DeptDO::getId).collect(Collectors.toList());
        //查1级结构下的二级结构
        for(DeptDO deptDO:firstDeptList){
            List<DeptDO> second=deptService.getDeptsByPid(deptDO.getId());
            DeptTreeRespVO respVO=new DeptTreeRespVO();
            respVO.setId(deptDO.getId());
            respVO.setPid(0l);
            respVO.setDeptName(deptDO.getDeptName());
            respVO.setChildren(DeptConvert.INSTANCE.convert(second));
            resultList.add(respVO);
        }
        //筛选2级机构
        List<DeptDO> secondDeptList=deptDOList.stream().filter(e->!e.getPid().equals(0l)).collect(Collectors.toList());
        for(DeptDO deptDO:secondDeptList){
            if(!pids.contains(deptDO.getPid())){//不包含，需要新增
                //先查1级结构
                DeptDO deptDO1=deptService.getDept(deptDO.getPid());
                List<DeptDO> second=deptService.getDeptsByPid(deptDO1.getId());
                DeptTreeRespVO respVO=new DeptTreeRespVO();
                respVO.setId(deptDO1.getId());
                respVO.setPid(0l);
                respVO.setDeptName(deptDO1.getDeptName());
                respVO.setChildren(DeptConvert.INSTANCE.convert(second));
            }
        }
        return success(resultList);

    }


}
