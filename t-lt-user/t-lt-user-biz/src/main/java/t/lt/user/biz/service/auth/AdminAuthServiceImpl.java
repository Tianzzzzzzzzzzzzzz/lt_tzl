package t.lt.user.biz.service.auth;

import cn.hutool.core.util.ObjectUtil;

import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import service.lt.common.enums.CommonStatusEnum;
import service.lt.common.enums.UserTypeEnum;
import t.lt.user.api.enums.auth.OAuth2ClientConstants;
import t.lt.user.api.enums.logger.LoginLogTypeEnum;
import t.lt.user.api.enums.logger.LoginResultEnum;
import t.lt.user.biz.controller.user.vo.AuthLoginReqVO;
import t.lt.user.biz.controller.user.vo.AuthLoginRespVO;
import t.lt.user.biz.convert.user.oauth2.AuthConvert;
import t.lt.user.biz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;
import t.lt.user.biz.service.oauth2.OAuth2TokenService;
import t.lt.user.biz.service.user.UserService;


import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Objects;

import static service.lt.common.exception.util.ServiceExceptionUtil.exception;
import static t.lt.user.api.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static t.lt.user.api.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;

@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private UserService userService;

    @Resource
    private OAuth2TokenService oauth2TokenService;



    @Resource
    private Validator validator;


    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
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












    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {

        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
    }



    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}
