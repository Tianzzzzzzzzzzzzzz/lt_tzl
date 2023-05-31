package t.lt.user.biz.controller.user.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@ApiModel("管理后台 - 用户创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateReqVO extends UserBaseVO {

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotEmpty(message = "密码不能为空")
    private String password;

}
