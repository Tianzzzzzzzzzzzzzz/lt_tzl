package t.lt.user.biz.dal.mysql.dept;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import service.lt.common.pojo.PageResult;
import service.lt.mybatis.core.mapper.BaseMapperX;
import service.lt.mybatis.core.query.LambdaQueryWrapperX;
import t.lt.user.biz.controller.dept.vo.DeptListReqVO;
import t.lt.user.biz.controller.user.vo.UserPageReqVO;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;


import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapperX<DeptDO> {

    default List<DeptDO> selectList(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .likeIfPresent(DeptDO::getDeptName, reqVO.getDeptName()));
    }

    default DeptDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapper<DeptDO>()
                .eq(DeptDO::getPid, parentId)
                .eq(DeptDO::getDeptName, name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(DeptDO::getPid, parentId);
    }

    @Select("SELECT COUNT(*) FROM system_dept WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

    default List<DeptDO> selectMallList() {
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .eq(DeptDO::getPid, 0));
    }


    default PageResult<DeptDO> selectPage(DeptListReqVO reqVO){
        return selectPage(reqVO, new LambdaQueryWrapperX<DeptDO>()
                .likeIfPresent(DeptDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(DeptDO::getPid, reqVO.getPid())
                .orderByDesc(DeptDO::getId));
    }

    default List<DeptDO> getDeptsByPid(Long pid){
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .eq(DeptDO::getPid, pid));

    }

}
