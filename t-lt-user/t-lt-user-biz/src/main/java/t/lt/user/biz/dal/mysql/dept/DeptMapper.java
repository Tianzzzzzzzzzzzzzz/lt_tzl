package t.lt.user.biz.dal.mysql.dept;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import service.lt.mybatis.core.mapper.BaseMapperX;
import service.lt.mybatis.core.query.LambdaQueryWrapperX;
import t.lt.user.biz.controller.dept.vo.DeptListReqVO;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;


import java.util.Date;
import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapperX<DeptDO> {

    default List<DeptDO> selectList(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .likeIfPresent(DeptDO::getName, reqVO.getName())
                .eqIfPresent(DeptDO::getMallId, reqVO.getMallId())
                .eqIfPresent(DeptDO::getStatus, reqVO.getStatus()));
    }

    default DeptDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapper<DeptDO>()
                .eq(DeptDO::getParentId, parentId)
                .eq(DeptDO::getName, name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(DeptDO::getParentId, parentId);
    }

    @Select("SELECT COUNT(*) FROM system_dept WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

    default List<DeptDO> selectMallList() {
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .eq(DeptDO::getParentId, 0));
    }

}
