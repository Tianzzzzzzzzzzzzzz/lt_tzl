package t.lt.user.biz.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.Length;

import javax.validation.constraints.NotEmpty;

@ApiModel("管理后台 - 用户个人中心更新密码 Request VO")
@Data
public class UserProfileUpdatePasswordReqVO {

    @ApiModelProperty(value = "旧密码", required = true, example = "123456")
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true, example = "654321")
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

}
