package t.lt.user.biz.dal.dataobject.dept;


import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import service.lt.common.enums.CommonStatusEnum;
import service.lt.mybatis.core.dataobject.BaseDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;


/**
 * 部门表
 *
 * @author ruoyi
 * @author 芋道源码
 */
@TableName("system_dept")
@KeySequence("system_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DeptDO extends BaseDO {

    /**
     * 部门ID
     */
    @TableId
    private Long id;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 父部门ID
     *
     * 关联 {@link #id}
     */
    private Long pid;

    /**
     * 部门状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
