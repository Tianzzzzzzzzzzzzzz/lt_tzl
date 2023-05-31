package t.lt.user.biz.controller.oauth2.vo.client;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import service.lt.common.util.json.JsonUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* OAuth2 客户端 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class OAuth2ClientBaseVO {

    @ApiModelProperty(value = "客户端编号", required = true, example = "tudou")
    @NotNull(message = "客户端编号不能为空")
    private String clientId;

    @ApiModelProperty(value = "客户端密钥", required = true, example = "fan")
    @NotNull(message = "客户端密钥不能为空")
    private String secret;

    @ApiModelProperty(value = "应用名", required = true, example = "土豆")
    @NotNull(message = "应用名不能为空")
    private String name;



    @ApiModelProperty(value = "应用描述", example = "我是一个应用")
    private String description;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "访问令牌的有效期", required = true, example = "8640")
    @NotNull(message = "访问令牌的有效期不能为空")
    private Integer accessTokenValiditySeconds;

    @ApiModelProperty(value = "刷新令牌的有效期", required = true, example = "8640000")
    @NotNull(message = "刷新令牌的有效期不能为空")
    private Integer refreshTokenValiditySeconds;


    @ApiModelProperty(value = "授权范围", example = "user_info")
    private List<String> scopes;

    @ApiModelProperty(value = "自动通过的授权范围", example = "user_info")
    private List<String> autoApproveScopes;

    @ApiModelProperty(value = "权限", example = "system:user:query")
    private List<String> authorities;

    @ApiModelProperty(value = "资源", example = "1024")
    private List<String> resourceIds;

    @ApiModelProperty(value = "附加信息", example = "{yunai: true}")
    private String additionalInformation;

    @AssertTrue(message = "附加信息必须是 JSON 格式")
    public boolean isAdditionalInformationJson() {
        return StrUtil.isEmpty(additionalInformation) || JsonUtils.isJson(additionalInformation);
    }

}
