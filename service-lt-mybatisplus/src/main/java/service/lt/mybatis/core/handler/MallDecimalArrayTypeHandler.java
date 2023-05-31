package service.lt.mybatis.core.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Decimal类型处理器 MyBatis使用类型处理器（type handler）将数据从数据库特定的数据类型转换为应用程序中的数据类型
 * 
 * @author reed
 * 
 */
public class MallDecimalArrayTypeHandler extends BaseTypeHandler<BigDecimal[]> {

	public MallDecimalArrayTypeHandler() {
		super();
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
                                    BigDecimal[] parameter, JdbcType jdbcType) throws SQLException {
		Array arr = ps.getConnection().createArrayOf("numeric", parameter);
		ps.setArray(i, arr);
	}

	@Override
	public BigDecimal[] getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Array array = rs.getArray(columnName);
		if (array == null || array.getArray() == null) {
			return null;
		}
		return (BigDecimal[]) array.getArray();
	}

	@Override
	public BigDecimal[] getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Array array = rs.getArray(columnIndex);
		if (array == null || array.getArray() == null) {
			return null;
		}
		return (BigDecimal[]) array.getArray();
	}

	@Override
	public BigDecimal[] getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Array array = cs.getArray(columnIndex);
		if (array == null || array.getArray() == null) {
			return null;
		}
		return (BigDecimal[]) array.getArray();
	}
}