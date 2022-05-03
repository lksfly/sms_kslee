package kr.bizdata.core.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class EmptyStringToNullHandler extends BaseTypeHandler<String> {
	/**
	 * String '' 을 Null로 처리 가능하게 함.
	 * @param ps
	 * @param i
	 * @param parameter
	 * @param JdbcType
	 * @throws SQLException
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType JdbcType) throws SQLException {
		if (parameter == null || parameter.length() == 0) {
			ps.setString(i, null);	
		} else {
			ps.setString(i, parameter);	
		}		
	}

	/**
	 * ResultSet을 Null 가능하게 처리함
	 * @param rs
	 * @param columnIndex
	 * @return rs
	 * @throws SQLException
	 */
	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getString(columnIndex);
	}

	/**
	 * CallableStatement을 Null 가능하게 처리함
	 * @param cs
	 * @param columnIndex
	 * @return cs
	 * @throws SQLException
	 */
	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex);
	}

	/**
	 * ResultSet을 Null 가능하게 처리함
	 * @param rs
	 * @param columnName
	 * @return rs
	 * @throws SQLException
	 */
	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getString(columnName);
	}
}