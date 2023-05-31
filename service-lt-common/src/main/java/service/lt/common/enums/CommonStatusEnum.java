package service.lt.common.enums;

import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author 芋道源码
 */
@Getter
//@AllArgsConstructor
public enum CommonStatusEnum {

    ENABLE(0, "开启"),
    DISABLE(1, "关闭");


    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    private CommonStatusEnum(Integer status,String name){
        this.status=status;
        this.name=name;
    }

    public static String getName(Integer status) {
        for (CommonStatusEnum type : values()) {
            if (type.status.equals(status)) {
                return type.name;
            }
        }
        throw new IllegalArgumentException("CommonStatusEnum can not find by status:" + status);
    }
}
