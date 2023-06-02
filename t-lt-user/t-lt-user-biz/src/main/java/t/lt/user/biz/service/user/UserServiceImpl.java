package t.lt.user.biz.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import service.lt.common.enums.CommonStatusEnum;
import service.lt.common.pojo.PageResult;
import service.lt.common.util.collection.CollectionUtils;
import service.lt.mybatis.core.query.QueryWrapperX;
import t.lt.user.biz.controller.user.vo.*;
import t.lt.user.biz.convert.user.UserConvert;
import t.lt.user.biz.dal.dataobject.permission.RoleDO;
import t.lt.user.biz.dal.dataobject.permission.UserRoleDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;
import t.lt.user.biz.dal.mysql.permission.RoleMapper;
import t.lt.user.biz.dal.mysql.permission.UserRoleMapper;
import t.lt.user.biz.dal.mysql.user.AdminUserMapper;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static service.lt.common.exception.util.ServiceExceptionUtil.exception;
import static service.lt.common.util.collection.CollectionUtils.convertSet;
import static t.lt.user.api.enums.ErrorCodeConstants.*;


/**
 * 部门 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class UserServiceImpl implements UserService {



    @Resource
    private AdminUserMapper userMapper;


    @Resource
    private PasswordEncoder passwordEncoder;



    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateReqVO reqVO) {

        // 插入用户
        AdminUserDO user = UserConvert.INSTANCE.convert(reqVO);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        user.setPassword(passwordEncoder.encode(reqVO.getPassword())); // 加密密码
        userMapper.insert(user);

        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateReqVO reqVO) {
        // 更新用户
        AdminUserDO updateObj = UserConvert.INSTANCE.convert(reqVO);
        updateObj.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        updateObj.setPassword(passwordEncoder.encode(reqVO.getPassword())); // 加密密码
        userMapper.updateById(updateObj);
    }


    @Override
    public void updateUserLogin(Long id, String loginIp) {
        userMapper.updateById(new AdminUserDO().setId(id).setLoginIp(loginIp).setLoginDate(new Date()));
    }



    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        // 校验旧密码密码
        checkOldPassword(id, reqVO.getOldPassword());
        // 执行更新
        //  AdminUserDO updateObj = new AdminUserDO().setId(id);
        AdminUserDO updateObj =new AdminUserDO();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(reqVO.getNewPassword())); // 加密密码
        userMapper.updateById(updateObj);
    }


    @Override
    public void updateUserPassword(Long id, String password) {
        // 校验用户存在
        checkUserExists(id);
        // 更新密码
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(password)); // 加密密码
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        // 校验用户存在
        checkUserExists(id);
        // 更新状态
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        userMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 校验用户存在
        checkUserExists(id);
        // 删除用户
        userMapper.deleteById(id);

    }

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }



    @Override
    public PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO) {
        return userMapper.selectPage(reqVO);
    }

    @Override
    public AdminUserDO getUser(Long id) {
        return userMapper.selectById(id);
    }





    @Override
    public List<AdminUserDO> getUsers(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public void validUsers(Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<AdminUserDO> users = userMapper.selectBatchIds(ids);
        Map<Long, AdminUserDO> userMap = CollectionUtils.convertMap(users, AdminUserDO::getId);
        // 校验
        ids.forEach(id -> {
            AdminUserDO user = userMap.get(id);
            if (user == null) {
                throw exception(USER_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus())) {
                throw exception(USER_IS_DISABLE, user.getNickName());
            }
        });
    }



    @Override
    public List<AdminUserDO> getUsersByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    @Override
    public List<AdminUserDO> getUsersByUsername(String username) {
        return userMapper.selectListByUsername(username);
    }





    @VisibleForTesting
    public void checkUserExists(Long id) {
        if (id == null) {
            return;
        }
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        AdminUserDO user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }





    /**
     * 校验旧密码
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    @VisibleForTesting
    public void checkOldPassword(Long id, String oldPassword) {
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }


    @Override
    public List<AdminUserDO> getUsersByStatus(Integer status) {
        return userMapper.selectListByStatus(status);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public List<UserListRespVO> getUserList(String nickName) {

        List<AdminUserDO> list = userMapper.selectListByNickname(nickName);
        List<UserListRespVO> userList = UserConvert.INSTANCE.convertList05(list);

        // 循环获取角色
        for (UserListRespVO user : userList) {
            Long id = user.getId();
            List<UserRoleDO> roleList = userRoleMapper.selectList("user_id", id);
            Set<Long> roleIdSet = roleList.stream().map(UserRoleDO::getRoleId).collect(Collectors.toSet());
            String roleName = "";
            if(null != roleIdSet && roleIdSet.size() > 0){
                QueryWrapperX<RoleDO> wrapper = new QueryWrapperX<>();
                wrapper.inIfPresent("id", roleIdSet);
                List<RoleDO> role = roleMapper.selectList(wrapper);
                roleName = role.stream().map(RoleDO::getRoleName).collect(Collectors.joining(","));
            }
            user.setUserRoleName(roleName);
        }

        return userList;
    }

    @Override
    public void enableUser(Long id) {
        AdminUserDO userDO=new AdminUserDO();
        userDO.setId(id);
        userDO.setStatus(0);
        userMapper.updateById(userDO);

    }

    @Override
    public void unableUser(Long id) {
        AdminUserDO userDO=new AdminUserDO();
        userDO.setId(id);
        userDO.setStatus(1);
        userMapper.updateById(userDO);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
