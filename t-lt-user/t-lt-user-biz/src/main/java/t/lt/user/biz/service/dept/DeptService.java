package t.lt.user.biz.service.dept;

import cn.hutool.core.collection.CollUtil;
import service.lt.common.pojo.PageResult;
import service.lt.common.util.collection.CollectionUtils;
import t.lt.user.biz.controller.dept.vo.DeptCreateReqVO;
import t.lt.user.biz.controller.dept.vo.DeptListReqVO;
import t.lt.user.biz.controller.dept.vo.DeptUpdateReqVO;
import t.lt.user.biz.controller.user.vo.UserPageReqVO;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.dal.dataobject.user.AdminUserDO;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 机构 Service 接口
 *
 * @author 芋道源码
 */
public interface DeptService {

    /**
     * 初始化机构的本地缓存
     */
    void initLocalCache();

    /**
     * 创建机构
     *
     * @param reqVO 机构信息
     * @return 机构编号
     */
    Long createDept(DeptCreateReqVO reqVO);

    /**
     * 更新机构
     *
     * @param reqVO 机构信息
     */
    void updateDept(DeptUpdateReqVO reqVO);

    /**
     * 删除机构
     *
     * @param id 机构编号
     */
    void deleteDept(Long id);

    /**
     * 筛选机构列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 机构列表
     */
    List<DeptDO> getSimpleDepts(DeptListReqVO reqVO);

    /**
     * 获得所有子机构，从缓存中
     *
     * @param parentId 机构编号
     * @param recursive 是否递归获取所有
     * @return 子机构列表
     */
    List<DeptDO> getDeptsByParentIdFromCache(Long parentId, boolean recursive);

    /**
     * 获得机构信息数组
     *
     * @param ids 机构编号数组
     * @return 机构信息数组
     */
    List<DeptDO> getDepts(Collection<Long> ids);

    /**
     * 获得机构信息
     *
     * @param id 机构编号
     * @return 机构信息
     */
    DeptDO getDept(Long id);

    /**
     * 校验机构们是否有效。如下情况，视为无效：
     * 1. 机构编号不存在
     * 2. 机构被禁用
     *
     * @param ids 角色编号数组
     */
    void validDepts(Collection<Long> ids);

    /**
     * 获得指定编号的机构列表
     *
     * @param ids 机构编号数组
     * @return 机构列表
     */
    List<DeptDO> getSimpleDepts(Collection<Long> ids);

    /**
     * 获得指定编号的机构 Map
     *
     * @param ids 机构编号数组
     * @return 机构 Map
     */
    default Map<Long, DeptDO> getDeptMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<DeptDO> list = getSimpleDepts(ids);
        return CollectionUtils.convertMap(list, DeptDO::getId);
    }


    /**
     * 获得机构分页列表
     *
     * @param reqVO 分页条件
     * @return 分页列表
     */
    PageResult<DeptDO> getDeptPage(DeptListReqVO reqVO);

    List<DeptDO> getDeptsByPid(Long pid);





}
