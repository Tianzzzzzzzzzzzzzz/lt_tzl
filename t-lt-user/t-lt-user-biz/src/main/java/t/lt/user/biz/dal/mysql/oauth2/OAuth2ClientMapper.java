package t.lt.user.biz.dal.mysql.oauth2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import service.lt.common.pojo.PageResult;
import service.lt.mybatis.core.mapper.BaseMapperX;
import service.lt.mybatis.core.query.LambdaQueryWrapperX;
import t.lt.user.biz.controller.dept.vo.DeptListReqVO;
import t.lt.user.biz.controller.oauth2.vo.client.OAuth2ClientPageReqVO;
import t.lt.user.biz.dal.dataobject.dept.DeptDO;
import t.lt.user.biz.dal.dataobject.oauth2.OAuth2ClientDO;


import java.util.Date;

/**
 * OAuth2 客户端 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {

    default PageResult<OAuth2ClientDO> selectPage(OAuth2ClientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OAuth2ClientDO>()
                .likeIfPresent(OAuth2ClientDO::getName, reqVO.getName())
                .eqIfPresent(OAuth2ClientDO::getStatus, reqVO.getStatus())
                .orderByDesc(OAuth2ClientDO::getId));
    }

    default OAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(OAuth2ClientDO::getClientId, clientId);
    }

    @Select("SELECT COUNT(*) FROM system_oauth2_client WHERE update_time > #{maxUpdateTime}")
    int selectCountByUpdateTimeGt(Date maxUpdateTime);

}
