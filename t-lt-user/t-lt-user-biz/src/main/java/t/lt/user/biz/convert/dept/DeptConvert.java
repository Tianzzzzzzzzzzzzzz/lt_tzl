package t.lt.user.biz.convert.dept;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import t.lt.user.biz.controller.dept.vo.*;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;


import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    List<DeptRespVO> convertList(List<DeptDO> list);

    List<DeptSimpleRespVO> convertList02(List<DeptDO> list);

    DeptRespVO convert(DeptDO bean);

    DeptDO convert(DeptCreateReqVO bean);

    DeptDO convert(DeptUpdateReqVO bean);


    List<DeptTreeRespVO> convert(List<DeptDO> list);

}
