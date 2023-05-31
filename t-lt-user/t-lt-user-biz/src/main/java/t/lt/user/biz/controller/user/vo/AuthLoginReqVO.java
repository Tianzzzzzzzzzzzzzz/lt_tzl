package t.lt.user.biz.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel("管理后台 - 账号密码登录 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginReqVO {

    @ApiModelProperty(value = "账号", required = true, example = "yudaoyuanma")
    @NotEmpty(message = "登录账号不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "buzhidao")
    @NotEmpty(message = "密码不能为空")
    private String password;



}
