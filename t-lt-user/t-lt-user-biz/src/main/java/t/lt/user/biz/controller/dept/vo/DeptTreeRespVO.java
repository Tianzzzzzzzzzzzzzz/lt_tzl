package t.lt.user.biz.controller.dept.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@ApiModel("管理后台 - 部门信息 Response VO")
@Data
public class DeptTreeRespVO   {

    @ApiModelProperty(value = "部门id", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "父id", required = true, example = "1024")
    private Long pid;
    @ApiModelProperty(value = "机构名称", required = true, example = "芋道")
    private String deptName;


   private List<DeptTreeRespVO> children;

}
