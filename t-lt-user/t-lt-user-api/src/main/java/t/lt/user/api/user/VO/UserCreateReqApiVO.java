package t.lt.user.api.user.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@ApiModel("管理后台 - 部门创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCreateReqApiVO extends UserBaseVO {

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotEmpty(message = "密码不能为空")
    private String password;
}
