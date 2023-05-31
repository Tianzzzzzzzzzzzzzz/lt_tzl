package t.lt.user.biz.service.user;

import t.lt.user.biz.controller.user.vo.AuthLoginReqVO;
import t.lt.user.biz.controller.user.vo.AuthLoginRespVO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;

import javax.validation.Valid;

public interface LoginServcie {


    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    AdminUserDO authenticate(String username, String password);

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);
}
