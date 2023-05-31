package t.lt.user.biz.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户列表带角色名称 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListRespVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "用户昵称", required = true, example = "芋道")
    private String nickname;

    @ApiModelProperty(value = "用户角色名称", required = true, example = "芋道")
    private String userRoleName;
}
