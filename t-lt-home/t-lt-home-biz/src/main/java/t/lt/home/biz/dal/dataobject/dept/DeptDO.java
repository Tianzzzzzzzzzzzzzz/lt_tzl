package t.lt.home.biz.dal.dataobject.dept;



import lombok.Data;
import lombok.ToString;


/**
 * 部门表
 *
 * @author ruoyi
 * @author 芋道源码
 */
@Data
@ToString
public class DeptDO  {

    /**
     * 部门ID
     */

    private Long id;
    private Long mallId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 父部门ID
     *
     * 关联 {@link #id}
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 负责人
     *
     */
    private Long leaderUserId;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 部门状态
     *
     * 枚举 {@link }
     */
    private Integer status;

}
