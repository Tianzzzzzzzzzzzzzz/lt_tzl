package service.lt.mybatis.core.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * Created by zhangxubo on 17/4/19.
 */
public class MallBooleanArrayTypeHandler extends BaseTypeHandler<Boolean[]> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean[] parameter, JdbcType jdbcType) throws SQLException {
        Array arr = ps.getConnection().createArrayOf("boolean", parameter);
        ps.setArray(i, arr);
    }

    @Override
    public Boolean[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Array array = rs.getArray(columnName);
        if (array == null || array.getArray() == null) {
            return null;
        }
        return (Boolean[]) array.getArray();
    }

    @Override
    public Boolean[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Array array = rs.getArray(columnIndex);
        if (array == null || array.getArray() == null) {
            return null;
        }
        return (Boolean[]) array.getArray();
    }

    @Override
    public Boolean[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Array array = cs.getArray(columnIndex);
        if (array == null || array.getArray() == null) {
            return null;
        }
        return (Boolean[]) array.getArray();
    }
}
