package t.lt.user.biz.service.permission;




import service.lt.common.pojo.PageResult;
import t.lt.user.biz.controller.permission.vo.menu.LogReqVO;
import t.lt.user.biz.controller.permission.vo.menu.MenuCreateReqVO;
import t.lt.user.biz.controller.permission.vo.menu.MenuListReqVO;
import t.lt.user.biz.controller.permission.vo.menu.MenuUpdateReqVO;
import t.lt.user.biz.dal.dataobject.permission.LogDO;
import t.lt.user.biz.dal.dataobject.permission.MenuDO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 日志 Service 接口
 *
 * @author 芋道源码
 */
public interface LogService {


    PageResult<LogDO> getLogPage(LogReqVO reqVO);

    void exportLogExcel(HttpServletResponse response, LogReqVO reqVO) throws IOException;

    void deletelogByIds(Collection<Long> ids);

}
