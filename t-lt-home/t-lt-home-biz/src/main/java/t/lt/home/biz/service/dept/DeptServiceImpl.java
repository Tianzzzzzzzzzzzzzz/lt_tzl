package t.lt.home.biz.service.dept;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import t.lt.home.biz.dal.dataobject.dept.DeptDO;


import java.util.*;



/**
 * 部门 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

 /*   @Resource
    private DeptMapper deptMapper;*/

    @Override
    public List<DeptDO> getMallList(){
       return null;
    }

}
