package t.lt.user.biz.controller.oauth2.vo.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import service.lt.common.pojo.PageParam;

@ApiModel("管理后台 - 访问令牌分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2AccessTokenPageReqVO extends PageParam {

    @ApiModelProperty(value = "用户编号", required = true, example = "666")
    private Long userId;

    @ApiModelProperty(value = "用户类型", required = true, example = "2", notes = "参见 UserTypeEnum 枚举")
    private Integer userType;

    @ApiModelProperty(value = "客户端编号", required = true, example = "2")
    private String clientId;

}