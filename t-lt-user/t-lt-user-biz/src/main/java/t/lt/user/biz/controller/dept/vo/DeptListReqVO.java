package t.lt.user.biz.controller.dept.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import service.lt.common.pojo.PageParam;

@ApiModel("管理后台 - 机构列表 Request VO")
@Data
public class DeptListReqVO  extends PageParam {

    @ApiModelProperty(value = "机构名称", example = "芋道", notes = "模糊匹配")
    private String deptName;



    @ApiModelProperty(value = "父id", example = "1", notes = "父id")
    private Long pid;

}
