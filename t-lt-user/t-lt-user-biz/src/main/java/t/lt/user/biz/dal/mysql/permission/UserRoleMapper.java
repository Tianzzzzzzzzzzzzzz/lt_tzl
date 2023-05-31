package t.lt.user.biz.dal.mysql.permission;

import service.lt.mybatis.core.mapper.BaseMapperX;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import t.lt.user.biz.dal.dataobject.permission.UserRoleDO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRoleDO> {

    default List<UserRoleDO> selectListByUserId(Long userId) {
        return selectList(new QueryWrapper<UserRoleDO>().eq("user_id", userId));
    }

    default List<UserRoleDO> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<UserRoleDO>().eq("role_id", roleId));
    }

    default void deleteListByUserIdAndRoleIdIds(Long userId, Collection<Long> roleIds) {
        delete(new QueryWrapper<UserRoleDO>().eq("user_id", userId)
                .in("role_id", roleIds));
    }

    default void deleteListByUserId(Long userId) {
        delete(new QueryWrapper<UserRoleDO>().eq("user_id", userId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<UserRoleDO>().eq("role_id", roleId));
    }


    default List<UserRoleDO> selectListByRoleIds(Collection<Long> roleIds) {
        return selectList(UserRoleDO::getRoleId, roleIds);
    }

    @Select("SELECT COUNT(*) FROM system_user_role WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
