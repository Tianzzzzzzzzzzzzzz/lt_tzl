package t.lt.user.biz.dal.mysql.permission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;
import service.lt.common.pojo.PageResult;
import service.lt.mybatis.core.dataobject.BaseDO;
import service.lt.mybatis.core.mapper.BaseMapperX;
import service.lt.mybatis.core.query.LambdaQueryWrapperX;
import t.lt.user.biz.controller.permission.vo.menu.LogReqVO;
import t.lt.user.biz.controller.permission.vo.role.RolePageReqVO;
import t.lt.user.biz.dal.dataobject.permission.LogDO;
import t.lt.user.biz.dal.dataobject.permission.LogDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface LogMapper extends BaseMapperX<LogDO> {


    default PageResult<LogDO> selectPage(LogReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LogDO>()
                .eqIfPresent(LogDO::getModuleId, reqVO.getModuleId())
                .likeIfPresent(LogDO::getOperateName, reqVO.getOperateName())
                .eqIfPresent(LogDO::getOperateType, reqVO.getOperateType())
                .eqIfPresent(LogDO::getOperateStatus, reqVO.getOperateStatus())
                .betweenIfPresent(LogDO::getOperateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .orderByDesc(LogDO::getId));
    }

    default List<LogDO> selectExcelList(LogReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<LogDO>()
                .eqIfPresent(LogDO::getModuleId, reqVO.getModuleId())
                .likeIfPresent(LogDO::getOperateName, reqVO.getOperateName())
                .eqIfPresent(LogDO::getOperateType, reqVO.getOperateType())
                .eqIfPresent(LogDO::getOperateStatus, reqVO.getOperateStatus())
                .betweenIfPresent(LogDO::getOperateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .orderByDesc(LogDO::getId));
    }
}
