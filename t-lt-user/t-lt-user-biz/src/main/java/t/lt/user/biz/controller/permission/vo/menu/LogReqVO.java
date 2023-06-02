package t.lt.user.biz.controller.permission.vo.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import service.lt.common.pojo.PageParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 菜单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class LogReqVO extends PageParam {

    @ApiModelProperty(value = "模块id",  example = "芋道")
    private Long moduleId;

    @ApiModelProperty(value = "操作人员", example = "张三", notes = "仅菜单类型为按钮时，才需要传递")
    private String operateName;

    @ApiModelProperty(value = "操作类型",  example = "1", notes = "参见 MenuTypeEnum 枚举类")
    private Integer operateType;

    @ApiModelProperty(value = "操作状态",  example = "1024")
    private Integer operateStatus;

    @ApiModelProperty(value = "开始时间",  example = "1024")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "post", notes = "仅菜单类型为菜单或者目录时，才需要传")
    private Date endTime;


}
