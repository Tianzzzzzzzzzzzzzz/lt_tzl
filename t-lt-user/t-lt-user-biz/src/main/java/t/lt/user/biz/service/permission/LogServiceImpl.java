package t.lt.user.biz.service.permission;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import service.lt.common.pojo.PageResult;

import t.lt.user.biz.controller.permission.vo.menu.LogReqVO;

import t.lt.user.biz.dal.dataobject.permission.LogDO;
import t.lt.user.biz.dal.mysql.permission.LogMapper;
import t.lt.user.biz.util.ExcelUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * 日志 Service 实现
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {



    @Resource
    private LogMapper logMapper;
    @Override
    public PageResult<LogDO> getLogPage(LogReqVO reqVO) {
        return logMapper.selectPage(reqVO);
    }

    @Override
    public void exportLogExcel(HttpServletResponse response, LogReqVO reqVO) throws IOException {
        List<LogDO> list=logMapper.selectExcelList(reqVO);
        for(LogDO contractDO:list){

        }
        ExcelUtils.write(response, "日志记录.xls", "日志记录", LogDO.class, list);
    }

    @Override
    public void deletelogByIds(Collection<Long> ids) {
        logMapper.deleteBatchIds(ids);
    }
}
