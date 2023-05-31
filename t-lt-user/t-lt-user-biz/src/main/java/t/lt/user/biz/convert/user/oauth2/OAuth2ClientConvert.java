package t.lt.user.biz.convert.user.oauth2;

import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;
import service.lt.common.pojo.PageResult;
import t.lt.user.biz.controller.oauth2.vo.client.OAuth2ClientCreateReqVO;
import t.lt.user.biz.controller.oauth2.vo.client.OAuth2ClientRespVO;
import t.lt.user.biz.controller.oauth2.vo.client.OAuth2ClientUpdateReqVO;
import t.lt.user.biz.dal.dataobject.oauth2.OAuth2ClientDO;

import java.util.List;

/**
 * OAuth2 客户端 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface OAuth2ClientConvert {

    OAuth2ClientConvert INSTANCE = Mappers.getMapper(OAuth2ClientConvert.class);

    OAuth2ClientDO convert(OAuth2ClientCreateReqVO bean);

    OAuth2ClientDO convert(OAuth2ClientUpdateReqVO bean);

    OAuth2ClientRespVO convert(OAuth2ClientDO bean);

    List<OAuth2ClientRespVO> convertList(List<OAuth2ClientDO> list);

    PageResult<OAuth2ClientRespVO> convertPage(PageResult<OAuth2ClientDO> page);

}
