package t.lt.user.api.enums.dept;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 机构编号枚举
 */
@Getter
@AllArgsConstructor
public enum DeptIdEnum {

    /**
     * 根节点
     */
    ROOT(0L);

    private final Long id;

}
