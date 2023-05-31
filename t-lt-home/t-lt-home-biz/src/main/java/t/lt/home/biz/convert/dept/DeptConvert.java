package t.lt.home.biz.convert.dept;


import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;
import t.lt.home.biz.dal.dataobject.dept.DeptDO;

import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    List<DeptDO> convertList(List<DeptDO> list);



}
