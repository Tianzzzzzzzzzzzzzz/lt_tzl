package t.lt.user.biz.controller.dept.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel("管理后台 - 部门信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptRespVO extends DeptBaseVO {

    @ApiModelProperty(value = "部门编号", required = true, example = "1024")
    private Long id;


    @ApiModelProperty(value = "上级机构名称", required = true, example = "芋道")
    private String pName;


    @ApiModelProperty(value = "创建时间", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Date createTime;


    @ApiModelProperty(value = "最后更新时间", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Date updateTime;

    @ApiModelProperty(value = "创建者",  example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private String creator;


    @ApiModelProperty(value = "更新者", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private String updater;


    @ApiModelProperty(value = "是否删除", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Boolean deleted;

}
