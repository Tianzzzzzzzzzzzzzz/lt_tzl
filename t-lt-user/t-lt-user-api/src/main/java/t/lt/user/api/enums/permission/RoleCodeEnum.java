package t.lt.user.api.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import service.lt.common.util.object.ObjectUtils;

/**
 * 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

  /*  public static boolean isSuperAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
    }*/
  public static boolean isSuperAdmin(String name) {
      return ObjectUtils.equalsAny(name, SUPER_ADMIN.getName());
  }
}
