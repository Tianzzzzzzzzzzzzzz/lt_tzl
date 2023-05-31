package t.lt.cms.biz.dal.mysql.dept;


import org.apache.ibatis.annotations.Mapper;
import t.lt.cms.biz.dal.dataobject.dept.DeptDO;

import java.util.List;

@Mapper
public interface DeptMapper {

    default List<DeptDO> selectMallList(){

        return null;
    }

}
