package t.lt.user.biz.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "管理后台 - 用户分页时的信息 Response VO", description = "相比用户基本信息来说，会多部门信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPageItemRespVO extends UserRespVO {

    @ApiModelProperty(value = "部门名称", required = true, example = "研发部")
    private String deptNname;

    @ApiModelProperty(value = "角色名称", required = true, example = "老大")
    private String roleName;
}
