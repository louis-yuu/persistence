package com.yunbao.sqlsession;

import com.yunbao.config.Configuration;
import com.yunbao.config.MappedStatement;

import java.lang.reflect.*;

/**
 * Created by louisyuu on 2020/2/26 下午3:58
 */
public class MapperProxy implements InvocationHandler {


    private SqlSession sqlSession;

    private Configuration configuration;

    public MapperProxy(SqlSession sqlSession, Configuration configuration) {
        this.sqlSession = sqlSession;
        this.configuration = configuration;
    }

    /**
     * @param proxy
     * @param method
     * @param args   即Object ...params
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 底层都还是去执行JDBC代码 //根据不同情况，来调用selctList或者selectOne
        // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
        String statementId = method.getDeclaringClass().getName() + "." + method.getName();

        MappedStatement ms = configuration.getMappedStatementMap().get(statementId);
        if (ms.getSqlAsLowerCase().startsWith("select")) {
            // 获取被调用方法的返回值类型
            Type genericReturnType = method.getGenericReturnType();
            // 判断是否进行了 泛型类型参数化
            if (genericReturnType instanceof ParameterizedType) {
                return sqlSession.selectList(statementId, args);
            } else {
                return sqlSession.selectOne(statementId, args);
            }
        } else if (ms.getSqlAsLowerCase().startsWith("insert")) {
            sqlSession.insert(statementId, args);
        } else if (ms.getSqlAsLowerCase().startsWith("update")) {
            sqlSession.update(statementId, args);
        } else if (ms.getSqlAsLowerCase().startsWith("delete")) {
            sqlSession.delete(statementId, args);
        }
        return null;
    }


}
