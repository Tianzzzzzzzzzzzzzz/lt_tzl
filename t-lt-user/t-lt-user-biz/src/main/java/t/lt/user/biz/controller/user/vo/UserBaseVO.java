package t.lt.user.biz.controller.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 部门 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class UserBaseVO {

    @ApiModelProperty(value = "用户姓名", required = true, example = "芋道")
    @NotBlank(message = "用户姓名不能为空")
    @Size(max = 10, message = "用户姓名长度不能超过10个字符")
    private String nickName;

    @ApiModelProperty(value = "机构 ID", example = "1024")
    @NotNull(message = "机构不能为空")
    private Long deptId;

    @ApiModelProperty(value = "机构 ID", example = "1024")
    @NotNull(message = "角色不能为空")
    private Long roleId;
    @ApiModelProperty(value = "账号", required = true, example = "芋道")
    @NotBlank(message = "账号不能为空")
    @Size(max = 10, message = "账号长度不能超过10个字符")
    private String userName;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 CommonStatusEnum 枚举")
   // @NotNull(message = "状态不能为空")
//    @InEnum(value = CommonStatusEnum.class, message = "修改状态必须是 {value}")
    private Integer status;



}
