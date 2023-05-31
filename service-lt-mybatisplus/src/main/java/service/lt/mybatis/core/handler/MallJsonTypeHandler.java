package service.lt.mybatis.core.handler;

import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Json类型处理器
 * 继承自BaseTypeHandler<Object> 
 * @author zhangzhiyong
 *
 */
public class MallJsonTypeHandler extends BaseTypeHandler<Object> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
                                    Object parameter, JdbcType jdbcType) throws SQLException {
		PGobject jsonObject = new PGobject();
		jsonObject.setType("json");
		jsonObject.setValue((String)parameter);
		ps.setObject(i, jsonObject);
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return JSONUtil.parseObj(rs.getString(columnName));
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return JSONUtil.parseObj(rs.getString(columnIndex));
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return JSONUtil.parseObj(cs.getString(columnIndex));
	}

}
