package t.lt.user.biz.dal.mysql.permission;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import service.lt.mybatis.core.mapper.BaseMapperX;
import t.lt.user.biz.dal.dataobject.permission.RoleMenuDO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMenuMapper extends BaseMapperX<RoleMenuDO> {

    @Repository
    class BatchInsertMapper extends ServiceImpl<RoleMenuMapper, RoleMenuDO> {
    }

    default List<RoleMenuDO> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<RoleMenuDO>().eq("role_id", roleId));
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new QueryWrapper<RoleMenuDO>().eq("role_id", roleId)
                .in("menu_id", menuIds));
    }

//    @Update("update system_role_menu set delete = 1 , update_time = #{date} WHERE menu_id in #{menuIds} and role_id = #{roleId}")
    @Update({"<script>",
            " update system_role_menu set deleted = 1 , update_time = #{date} WHERE role_id = #{roleId} and menu_id in ",
            " <foreach collection=\"menuIds\" item=\"menuId\" index=\"index\" open=\"(\" separator=\",\" close=\")\"> ",
            " #{menuId} ",
            " </foreach> ",
            "</script>"})
    Long deleteListByRoleIdAndMenuIds(@Param("date") Date date, @Param("roleId") Long roleId, @Param("menuIds") Collection<Long> menuIds);

    default void deleteListByMenuId(Long menuId) {
        delete(new QueryWrapper<RoleMenuDO>().eq("menu_id", menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<RoleMenuDO>().eq("role_id", roleId));
    }

    @Select("SELECT COUNT(*) FROM system_role_menu WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
