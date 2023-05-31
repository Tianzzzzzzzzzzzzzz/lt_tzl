package t.lt.user.biz.convert.user.oauth2;

import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;
import service.lt.common.pojo.PageResult;
import t.lt.user.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import t.lt.user.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import t.lt.user.biz.controller.oauth2.vo.token.OAuth2AccessTokenRespVO;
import t.lt.user.biz.dal.dataobject.oauth2.OAuth2AccessTokenDO;


@Mapper
public interface OAuth2TokenConvert {

    OAuth2TokenConvert INSTANCE = Mappers.getMapper(OAuth2TokenConvert.class);

    OAuth2AccessTokenCheckRespDTO convert(OAuth2AccessTokenDO bean);

    PageResult<OAuth2AccessTokenRespVO> convert(PageResult<OAuth2AccessTokenDO> page);

    OAuth2AccessTokenRespDTO convert2(OAuth2AccessTokenDO bean);

}
