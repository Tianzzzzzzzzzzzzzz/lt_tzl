package t.lt.home.biz.service.dept;


import t.lt.home.biz.dal.dataobject.dept.DeptDO;

import java.util.List;

/**
 * 部门 Service 接口
 *
 * @author 芋道源码
 */
public interface DeptService {



    /**
     * 获得一级部门（即商场）列表
     */
    List<DeptDO> getMallList();
}
