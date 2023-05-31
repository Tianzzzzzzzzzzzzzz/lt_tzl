package t.lt.user.biz.dal.mysql.user;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import service.lt.common.pojo.PageResult;
import service.lt.mybatis.core.mapper.BaseMapperX;
import service.lt.mybatis.core.query.LambdaQueryWrapperX;
import t.lt.user.biz.controller.user.vo.UserPageReqVO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;


import java.util.Collection;
import java.util.List;

@Mapper
public interface AdminUserMapper extends BaseMapperX<AdminUserDO> {

    default AdminUserDO selectByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<AdminUserDO>().eq(AdminUserDO::getUsername, username));
    }

    default AdminUserDO selectByEmail(String email) {
        return selectOne(new LambdaQueryWrapper<AdminUserDO>().eq(AdminUserDO::getEmail, email));
    }

    default AdminUserDO selectByMobile(String mobile) {
        return selectOne(new LambdaQueryWrapper<AdminUserDO>().eq(AdminUserDO::getMobile, mobile));
    }

    default PageResult<AdminUserDO> selectPage(UserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AdminUserDO>()
                .likeIfPresent(AdminUserDO::getUsername, reqVO.getUsername())
                .likeIfPresent(AdminUserDO::getMobile, reqVO.getMobile())
                .eqIfPresent(AdminUserDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(AdminUserDO::getCreateTime, reqVO.getBeginTime(), reqVO.getEndTime())
                .orderByDesc(AdminUserDO::getId));

    }



    default List<AdminUserDO> selectListByNickname(String nickname) {
        return selectList(new LambdaQueryWrapperX<AdminUserDO>().like(AdminUserDO::getNickname, nickname));
    }

    default List<AdminUserDO> selectListByUsername(String username) {
        return selectList(new LambdaQueryWrapperX<AdminUserDO>().like(AdminUserDO::getUsername, username));
    }

    default List<AdminUserDO> selectListByStatus(Integer status) {
        return selectList(AdminUserDO::getStatus, status);
    }



}

