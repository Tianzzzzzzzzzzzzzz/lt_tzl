package t.lt.user.api.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.lt.common.pojo.CommonResult;

import t.lt.user.api.user.dto.UserRespDTO;
import t.lt.user.api.enums.ApiConstants;

import java.util.Collection;
import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
public interface UserApi {

    String PREFIX = ApiConstants.PREFIX + "/dept";

    @GetMapping(PREFIX + "/get")
    CommonResult<UserRespDTO> getDept(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/list")
    CommonResult<List<UserRespDTO>> getDepts(@RequestParam("ids") Collection<Long> ids);

    @GetMapping(PREFIX + "/valid")
    CommonResult<Boolean> validDepts(@RequestParam("ids") Collection<Long> ids);



}
