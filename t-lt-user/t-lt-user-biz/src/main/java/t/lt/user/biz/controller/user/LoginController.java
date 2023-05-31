package t.lt.user.biz.controller.user;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import service.lt.common.enums.CommonStatusEnum;
import service.lt.common.pojo.CommonResult;
import service.lt.common.util.collection.SetUtils;
import t.lt.user.api.enums.logger.LoginLogTypeEnum;
import t.lt.user.api.enums.permission.MenuTypeEnum;
import t.lt.user.biz.controller.user.vo.*;
import t.lt.user.biz.convert.user.oauth2.AuthConvert;
import t.lt.user.biz.dal.dataobject.permission.MenuDO;
import t.lt.user.biz.dal.dataobject.permission.RoleDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;
import t.lt.user.biz.framework.security.SecurityProperties;
import t.lt.user.biz.service.auth.AdminAuthService;
import t.lt.user.biz.service.permission.PermissionService;
import t.lt.user.biz.service.permission.RoleService;
import t.lt.user.biz.service.user.LoginServcie;
import t.lt.user.biz.service.user.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Set;

import static service.lt.common.pojo.CommonResult.success;
import static service.lt.common.util.collection.CollectionUtils.singleton;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServcie loginServcie;

    @Autowired
    private AdminAuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    public static final String AUTHORIZATION_BEARER = "Bearer";
    private final SecurityProperties securityProperties;

    public static final String LOGIN_USER_HEADER = "login-user";

    public LoginController(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(loginServcie.login(reqVO));
    }

    @PostMapping("/logout")
    @ApiOperation("登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
       String token = obtainAuthorization(request, securityProperties.getTokenHeader());

        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @ApiOperation("刷新令牌")
    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    @GetMapping("/get-permission-info")
    @ApiOperation("获取登录用户的权限信息")
    public CommonResult<AuthPermissionInfoRespVO> getPermissionInfo() {
        // 获得用户信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        if (user == null) {
            return null;
        }
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        List<RoleDO> roleList = roleService.getRolesFromCache(roleIds);
        // 获得菜单列表
        List<MenuDO> menuList = permissionService.getRoleMenuListFromCache(roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType(), MenuTypeEnum.BUTTON.getType()),
                singleton(CommonStatusEnum.ENABLE.getStatus())); // 只要开启的
        // 拼接结果返回
        return success(AuthConvert.INSTANCE.convert(user, roleList, menuList));
    }

    @GetMapping("/list-menus")
    @ApiOperation("获得登录用户的菜单列表")
    public CommonResult<List<AuthMenuRespVO>> getMenus() {
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 获得用户拥有的菜单列表
        List<MenuDO> menuList = permissionService.getRoleMenuListFromCache(roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType()), // 只要目录和菜单类型
                singleton(CommonStatusEnum.ENABLE.getStatus())); // 只要开启的
        // 转换成 Tree 结构返回
        return success(AuthConvert.INSTANCE.buildMenuTree(menuList));
    }
    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @Nullable
    public static LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof LoginUser ? (LoginUser) authentication.getPrincipal() : null;
    }

    /**
     * 获得当前用户的编号，从上下文中
     *
     * @return 用户编号
     */
    @Nullable
    public static Long getLoginUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getId() : null;
    }

    /**
     * 从请求中，获得认证 Token
     *
     * @param request 请求
     * @param header 认证 Token 对应的 Header 名字
     * @return 认证 Token
     */
    public static String obtainAuthorization(HttpServletRequest request, String header) {
        String authorization = request.getHeader(header);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
        if (index == -1) { // 未找到
            return null;
        }
        return authorization.substring(index + 7).trim();
    }
}
