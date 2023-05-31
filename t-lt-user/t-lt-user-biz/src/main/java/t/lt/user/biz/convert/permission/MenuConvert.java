package t.lt.user.biz.convert.permission;


import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;
import t.lt.user.biz.controller.permission.vo.menu.MenuCreateReqVO;
import t.lt.user.biz.controller.permission.vo.menu.MenuRespVO;
import t.lt.user.biz.controller.permission.vo.menu.MenuSimpleRespVO;
import t.lt.user.biz.controller.permission.vo.menu.MenuUpdateReqVO;
import t.lt.user.biz.dal.dataobject.permission.MenuDO;


import java.util.List;

@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convertList(List<MenuDO> list);

    MenuDO convert(MenuCreateReqVO bean);

    MenuDO convert(MenuUpdateReqVO bean);

    MenuRespVO convert(MenuDO bean);

    List<MenuSimpleRespVO> convertList02(List<MenuDO> list);

}
