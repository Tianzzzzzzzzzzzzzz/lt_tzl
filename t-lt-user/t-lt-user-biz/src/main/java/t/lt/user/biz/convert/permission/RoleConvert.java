package t.lt.user.biz.convert.permission;


import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;
import t.lt.user.biz.controller.permission.vo.role.RoleCreateReqVO;
import t.lt.user.biz.controller.permission.vo.role.RoleRespVO;
import t.lt.user.biz.controller.permission.vo.role.RoleSimpleRespVO;
import t.lt.user.biz.controller.permission.vo.role.RoleUpdateReqVO;
import t.lt.user.biz.dal.dataobject.permission.RoleDO;


import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleDO convert(RoleUpdateReqVO bean);

    RoleRespVO convert(RoleDO bean);

    RoleDO convert(RoleCreateReqVO bean);

    List<RoleSimpleRespVO> convertList02(List<RoleDO> list);



}
