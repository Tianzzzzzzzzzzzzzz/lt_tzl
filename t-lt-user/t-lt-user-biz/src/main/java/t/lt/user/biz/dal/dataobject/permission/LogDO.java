package t.lt.user.biz.dal.dataobject.permission;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import service.lt.common.enums.CommonStatusEnum;
import service.lt.mybatis.core.dataobject.BaseDO;

import java.util.Date;

/**
 * 日志 DO
 *
 * @author ruoyi
 */
@TableName(value = "system_log", autoResultMap = true)
@KeySequence("system_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
public class LogDO  {

    /**
     * 角色ID
     */
    @TableId
    private Long id;
    /**
     * 系统模块id
     */
    private Long moduleId;
    /**
     * 角色标识
     */
    private String operateName;
    /**
     * 角色排序
     */
    private Integer operateType;
    /**
     * 角色状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer operateStatus;
    /**
     * 角色排序
     */
    private Date operateTime;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求方式
     */
    private String httpMethod;
    /**
     * 登录信息
     */
    private String loginIp;
    /**
     * 操作方法
     */
    private String operateMethod;
    /**
     * 入参
     */
    private String requestParams;
    /**
     * 出参
     */
    private String responseParams;

}
