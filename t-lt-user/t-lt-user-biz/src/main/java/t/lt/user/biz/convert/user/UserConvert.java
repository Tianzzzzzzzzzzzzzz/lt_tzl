package t.lt.user.biz.convert.user;


import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;
import t.lt.user.biz.controller.user.vo.*;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    List<DeptDO> convertList(List<DeptDO> list);

    AdminUserDO convert(UserCreateReqVO reqVO);
    AdminUserDO convert(UserProfileUpdateReqVO reqVO);
    AdminUserDO convert(UserUpdateReqVO reqVO);

    List<UserListRespVO> convertList05( List<AdminUserDO> list);
    UserPageItemRespVO convert(AdminUserDO bean);

    UserPageItemRespVO.Dept convert(DeptDO bean);


}
