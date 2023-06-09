package t.lt.user.biz.dal.mysql.permission;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import service.lt.mybatis.core.mapper.BaseMapperX;
import service.lt.mybatis.core.query.LambdaQueryWrapperX;
import t.lt.user.biz.controller.permission.vo.menu.MenuListReqVO;
import t.lt.user.biz.dal.dataobject.permission.MenuDO;

import java.util.Date;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    default MenuDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapper<MenuDO>().eq(MenuDO::getParentId, parentId)
                .eq(MenuDO::getName, name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(MenuDO::getParentId, parentId);
    }

    default List<MenuDO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MenuDO>().likeIfPresent(MenuDO::getName, reqVO.getName())
                .eqIfPresent(MenuDO::getStatus, reqVO.getStatus()));
    }

    @Select("SELECT COUNT(*) FROM system_menu WHERE update_time >= #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
