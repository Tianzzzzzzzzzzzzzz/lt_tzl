package t.lt.user.biz.service.dept;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import service.lt.common.enums.CommonStatusEnum;
import service.lt.common.pojo.PageResult;
import service.lt.common.util.collection.CollectionUtils;
import t.lt.user.api.enums.dept.DeptIdEnum;
import t.lt.user.biz.controller.dept.vo.DeptCreateReqVO;
import t.lt.user.biz.controller.dept.vo.DeptListReqVO;
import t.lt.user.biz.controller.dept.vo.DeptUpdateReqVO;
import t.lt.user.biz.convert.dept.DeptConvert;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.dal.mysql.dept.DeptMapper;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static service.lt.common.exception.util.ServiceExceptionUtil.exception;
import static t.lt.user.api.enums.ErrorCodeConstants.*;

//import t.uamll.system.biz.mq.producer.dept.DeptProducer;

/**
 * 机构 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 机构缓存
     * key：机构编号 {@link DeptDO#getId()}
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @SuppressWarnings("FieldCanBeLocal")
    private volatile Map<Long, DeptDO> deptCache;
    /**
     * 父机构缓存
     * key：机构编号 {@link DeptDO#getPid()} ()}
     * value: 直接子机构列表
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    private volatile Multimap<Long, DeptDO> parentDeptCache;
    /**
     * 缓存机构的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Resource
    private DeptMapper deptMapper;

//    @Resource
//    private DeptProducer deptProducer;

    @Resource
    @Lazy // 注入自己，所以延迟加载
    private DeptService self;



    @Override
    @PostConstruct
    //@TenantIgnore // 初始化缓存，无需租户过滤
    public synchronized void initLocalCache() {
        // 获取机构列表，如果有更新
        List<DeptDO> deptList = loadDeptIfUpdate(maxUpdateTime);
        if (CollUtil.isEmpty(deptList)) {
            return;
        }

        // 构建缓存
        ImmutableMap.Builder<Long, DeptDO> builder = ImmutableMap.builder();
        ImmutableMultimap.Builder<Long, DeptDO> parentBuilder = ImmutableMultimap.builder();
        deptList.forEach(sysRoleDO -> {
            builder.put(sysRoleDO.getId(), sysRoleDO);
            parentBuilder.put(sysRoleDO.getPid(), sysRoleDO);
        });
        // 设置缓存
        deptCache = builder.build();
        parentDeptCache = parentBuilder.build();
        maxUpdateTime = CollectionUtils.getMaxValue(deptList, DeptDO::getUpdateTime);
        log.info("[initLocalCache][初始化 Dept 数量为 {}]", deptList.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        self.initLocalCache();
    }

    /**
     * 如果机构发生变化，从数据库中获取最新的全量机构。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前机构的最大更新时间
     * @return 机构列表
     */
    protected List<DeptDO> loadDeptIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadMenuIfUpdate][首次加载全量机构]");
        } else { // 判断数据库中是否有更新的机构
            if (deptMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                return null;
            }
            log.info("[loadMenuIfUpdate][增量加载全量机构]");
        }
        // 第二步，如果有更新，则从数据库加载所有机构
        return deptMapper.selectList();
    }

    @Override
    public Long createDept(DeptCreateReqVO reqVO) {
        // 校验正确性
        if (reqVO.getPid() == null) {
            reqVO.setPid(DeptIdEnum.ROOT.getId());
        }
        checkCreateOrUpdate(null, reqVO.getPid(), reqVO.getDeptName());
        // 插入机构
        DeptDO dept = DeptConvert.INSTANCE.convert(reqVO);
        deptMapper.insert(dept);


        return dept.getId();
    }

    @Override
    public void updateDept(DeptUpdateReqVO reqVO) {
        // 校验正确性
        if (reqVO.getPid() == null) {
            reqVO.setPid(DeptIdEnum.ROOT.getId());
        }
        checkCreateOrUpdate(reqVO.getId(), reqVO.getPid(), reqVO.getDeptName());
        // 更新机构
        DeptDO updateObj = DeptConvert.INSTANCE.convert(reqVO);
        deptMapper.updateById(updateObj);

    }

    @Override
    public void deleteDept(Long id) {
        // 校验是否存在
        checkDeptExists(id);
        // 校验是否有子机构
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // 删除机构
        deptMapper.deleteById(id);

    }

    @Override
    public List<DeptDO> getSimpleDepts(DeptListReqVO reqVO) {
        return deptMapper.selectList(reqVO);
    }

    @Override
    public List<DeptDO> getDeptsByParentIdFromCache(Long parentId, boolean recursive) {
        if (parentId == null) {
            return Collections.emptyList();
        }
        List<DeptDO> result = new ArrayList<>(); // TODO 芋艿：待优化，新增缓存，避免每次遍历的计算
        // 递归，简单粗暴
        this.getDeptsByParentIdFromCache(result, parentId,
                recursive ? Integer.MAX_VALUE : 1, // 如果递归获取，则无限；否则，只递归 1 次
                parentDeptCache);
        return result;
    }

    /**
     * 递归获取所有的子机构，添加到 result 结果
     *
     * @param result 结果
     * @param parentId 父编号
     * @param recursiveCount 递归次数
     * @param parentDeptMap 父机构 Map，使用缓存，避免变化
     */
    private void getDeptsByParentIdFromCache(List<DeptDO> result, Long parentId, int recursiveCount,
                                             Multimap<Long, DeptDO> parentDeptMap) {
        // 递归次数为 0，结束！
        if (recursiveCount == 0) {
            return;
        }
        // 获得子机构
        Collection<DeptDO> depts = parentDeptMap.get(parentId);
        if (CollUtil.isEmpty(depts)) {
            return;
        }
        result.addAll(depts);
        // 继续递归
        depts.forEach(dept -> getDeptsByParentIdFromCache(result, dept.getId(),
                recursiveCount - 1, parentDeptMap));
    }

    private void checkCreateOrUpdate(Long id, Long parentId, String name) {
        // 校验自己存在
        checkDeptExists(id);
        // 校验父机构的有效性
        checkParentDeptEnable(id, parentId);
        // 校验机构名的唯一性
        checkDeptNameUnique(id, parentId, name);
    }

    private void checkParentDeptEnable(Long id, Long parentId) {
        if (parentId == null || DeptIdEnum.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能设置自己为父机构
        if (parentId.equals(id)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 父岗位不存在
        DeptDO dept = deptMapper.selectById(parentId);
        if (dept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 父机构被禁用
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
            throw exception(DEPT_NOT_ENABLE);
        }
        // 父机构不能是原来的子机构
        List<DeptDO> children = this.getDeptsByParentIdFromCache(id, true);
        if (children.stream().anyMatch(dept1 -> dept1.getId().equals(parentId))) {
            throw exception(DEPT_PARENT_IS_CHILD);
        }
    }

    private void checkDeptExists(Long id) {
        if (id == null) {
            return;
        }
        DeptDO dept = deptMapper.selectById(id);
        if (dept == null) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    private void checkDeptNameUnique(Long id, Long parentId, String name) {
        DeptDO menu = deptMapper.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public List<DeptDO> getDepts(Collection<Long> ids) {
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public DeptDO getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public void validDepts(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        List<DeptDO> depts = deptMapper.selectBatchIds(ids);
        Map<Long, DeptDO> deptMap = CollectionUtils.convertMap(depts, DeptDO::getId);
        // 校验
        ids.forEach(id -> {
            DeptDO dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw exception(DEPT_NOT_ENABLE, dept.getDeptName());
            }
        });
    }

    @Override
    public List<DeptDO> getSimpleDepts(Collection<Long> ids) {
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DeptDO> getDeptPage(DeptListReqVO reqVO) {
        DeptDO deptDO=new DeptDO();
        //二级机构
        PageResult<DeptDO> pageResult= deptMapper.selectPage(reqVO);
        //再查一级机构
        if(reqVO.getPid()!=null){
            deptDO=deptMapper.selectById(reqVO.getPid());
            Long total=pageResult.getTotal();
            pageResult.getList().add(deptDO);
            BigDecimal add=new BigDecimal(total).add(new BigDecimal(1));
            pageResult.setTotal(add.longValue());
        }
       return pageResult;

    }
    @Override
    public  List<DeptDO> getDeptsByPid(Long pid){
        return  deptMapper.getDeptsByPid(pid);
    }


}
