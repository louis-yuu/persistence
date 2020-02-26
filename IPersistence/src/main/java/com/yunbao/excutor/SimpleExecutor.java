package com.yunbao.excutor;

import com.yunbao.config.BoundSql;
import com.yunbao.config.Configuration;
import com.yunbao.config.MappedStatement;
import com.yunbao.utils.GenericTokenParser;
import com.yunbao.utils.ParameterMapping;
import com.yunbao.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 下午1:04
 */
public class SimpleExecutor implements Executor {

    private Connection connection;

    @Override
    public <T> List<T> execute(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        connection = configuration.getDataSource().getConnection();
        String rawSql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(rawSql);
        PreparedStatement ps = connection.prepareStatement(boundSql.getSqlText());

        if (params != null && params.length > 0) {
            if (params[0].getClass().getName().equals(Integer.class.getName())) {
                ps.setInt(1, (Integer) params[0]);
            } else {
                int paramIndex = 1;
                for (int i = 0; i < boundSql.getParameterMappings().size(); i++) {
                    ParameterMapping parameterMapping = boundSql.getParameterMappings().get(i);
                    //java bean的属性名要和数据库字段名对应
                    String fieldName = parameterMapping.getContent();
                    //对应的属性
                    Field field = mappedStatement.getParameterTypeClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    //参数值
                    Object value = field.get(params[0]);
                    ps.setObject(paramIndex++, value);
                }
            }
        }
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        Class<?> resultType = mappedStatement.getResultTypeClass();
        List<T> results = new ArrayList<>();
        while (resultSet != null && resultSet.next()) {
            T resultObj = (T) resultType.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            //循环每一列
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //获取列名
                String columnName = metaData.getColumnName(i);
                //获取列值
                Object value = resultSet.getObject(columnName);
                //设置值到返回对象
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                propertyDescriptor.getWriteMethod().invoke(resultObj, value);
            }
            results.add(resultObj);
        }
        return results;
    }


    @Override
    public void close() throws SQLException {
        connection.close();
    }


    private BoundSql getBoundSql(String sql) {

        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parse, parameterMappings);
    }


}