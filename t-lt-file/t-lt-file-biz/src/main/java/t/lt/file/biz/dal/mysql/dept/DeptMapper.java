package t.lt.file.biz.dal.mysql.dept;


import org.apache.ibatis.annotations.Mapper;
import t.lt.file.biz.dal.dataobject.dept.DeptDO;

import java.util.List;

@Mapper
public interface DeptMapper {

    default List<DeptDO> selectMallList(){

        return null;
    }

}
