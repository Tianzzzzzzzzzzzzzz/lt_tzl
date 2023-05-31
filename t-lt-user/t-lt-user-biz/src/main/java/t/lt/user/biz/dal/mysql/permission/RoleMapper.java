package t.lt.user.biz.dal.mysql.permission;

import service.lt.common.pojo.PageResult;
import service.lt.mybatis.core.dataobject.BaseDO;
import service.lt.mybatis.core.mapper.BaseMapperX;
import service.lt.mybatis.core.query.LambdaQueryWrapperX;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;
import t.lt.user.biz.controller.permission.vo.role.RolePageReqVO;
import t.lt.user.biz.dal.dataobject.permission.RoleDO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapperX<RoleDO> {


    default RoleDO selectByName(String name) {
        return selectOne(RoleDO::getName, name);
    }

    default RoleDO selectByCode(String code) {
        return selectOne(RoleDO::getCode, code);
    }

    default List<RoleDO> selectListByStatus(@Nullable Collection<Integer> statuses) {
        return selectList(RoleDO::getStatus, statuses);
    }

    @Select("SELECT COUNT(*) FROM system_role WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);
    default PageResult<RoleDO> selectPage(RolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoleDO>()
                .likeIfPresent(RoleDO::getName, reqVO.getName())
                .likeIfPresent(RoleDO::getCode, reqVO.getCode())
                .eqIfPresent(RoleDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoleDO::getId));
    }
}
