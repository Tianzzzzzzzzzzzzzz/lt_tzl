package service.lt.mybatis.core.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 *  Long类型处理器
 * MyBatis使用类型处理器（type handler）将数据从数据库特定的数据类型转换为应用程序中的数据类型，
 * 这样你就可以创建一个以一种尽可能透明的方式来使用数据库的应用程序。
 * 类型处理器本质上就是一个翻译器（translator）--他将数据库返回的结果集合中的列“翻译”为相应的JavaBean中的字段。
 * @author Luke
 *
 */
public class MallLongArrayTypeHandler extends BaseTypeHandler<Long[]> {

    public MallLongArrayTypeHandler() {
        super();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    Long[] parameter, JdbcType jdbcType) throws SQLException {
    	Array arr = ps.getConnection().createArrayOf("bigint", parameter);
        ps.setArray(i, arr);
    }

    @Override
    public Long[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Array array = rs.getArray(columnName);
        if(array == null || array.getArray() == null) {
        	return null;
        }
        return (Long[])array.getArray();
    }

    @Override
    public Long[] getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        Array array = rs.getArray(columnIndex);
        if(array == null || array.getArray() == null) {
        	return null;
        }
        return (Long[])array.getArray();
    }

    @Override
    public Long[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Array array = cs.getArray(columnIndex);
        if(array == null || array.getArray() == null) {
        	return null;
        }
        return (Long[])array.getArray();
    }
}