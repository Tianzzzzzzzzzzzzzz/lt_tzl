package t.lt.user.biz.service.user;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import service.lt.common.enums.CommonStatusEnum;
import service.lt.common.enums.UserTypeEnum;
import t.lt.user.api.enums.auth.OAuth2ClientConstants;
import t.lt.user.api.enums.logger.LoginLogTypeEnum;
import t.lt.user.biz.controller.user.vo.AuthLoginReqVO;
import t.lt.user.biz.controller.user.vo.AuthLoginRespVO;
import t.lt.user.biz.convert.user.oauth2.AuthConvert;
import t.lt.user.biz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;
import t.lt.user.biz.service.oauth2.OAuth2TokenService;

import javax.annotation.Resource;

import static service.lt.common.exception.util.ServiceExceptionUtil.exception;
import static t.lt.user.api.enums.ErrorCodeConstants.*;


@Service
public class LoginServiceImpl implements LoginServcie {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OAuth2TokenService oauth2TokenService;
    @Autowired
    private UserService userService;
    @Override
    public AdminUserDO authenticate(String username, String password) {
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {

        // 使用账号密码，进行登录
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }
    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {

        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }
    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}


