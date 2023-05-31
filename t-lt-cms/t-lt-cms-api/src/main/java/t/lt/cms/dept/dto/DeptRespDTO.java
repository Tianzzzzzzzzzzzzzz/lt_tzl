package t.lt.cms.dept.dto;

import service.lt.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * 部门 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class DeptRespDTO {

    /**
     * 部门编号
     */
    private Long id;
    /**
     * 部门状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
