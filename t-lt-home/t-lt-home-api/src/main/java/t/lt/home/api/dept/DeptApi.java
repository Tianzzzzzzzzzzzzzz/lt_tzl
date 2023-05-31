package t.lt.home.api.dept;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.lt.common.pojo.CommonResult;

import t.lt.home.api.dept.dto.DeptRespDTO;
import t.lt.home.api.enums.ApiConstants;

import java.util.Collection;
import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
public interface DeptApi {

    String PREFIX = ApiConstants.PREFIX + "/dept";

    @GetMapping(PREFIX + "/get")
    CommonResult<DeptRespDTO> getDept(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/list")
    CommonResult<List<DeptRespDTO>> getDepts(@RequestParam("ids") Collection<Long> ids);

    @GetMapping(PREFIX + "/valid")
    CommonResult<Boolean> validDepts(@RequestParam("ids") Collection<Long> ids);



}
