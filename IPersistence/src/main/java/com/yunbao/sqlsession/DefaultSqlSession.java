package com.yunbao.sqlsession;

import com.yunbao.config.Configuration;
import com.yunbao.config.MappedStatement;
import com.yunbao.excutor.Executor;
import com.yunbao.excutor.SimpleExecutor;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 下午1:01
 */
public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;

    private Executor executor;


    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;

        this.executor = new SimpleExecutor();
    }

    @Override
    public <T> List<T> selectList(String statementId, Object... params) throws Exception {
        MappedStatement ms = configuration.getMappedStatementMap().get(statementId);
        return executor.execute(configuration, ms, params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<T> results = selectList(statementId, params);
        if (results.size() > 1) {
            throw new RuntimeException("Too many results");
        }
        return results.get(0);
    }

    @Override
    public void insert(String statementId, Object... params) throws Exception {
        selectList(statementId, params);
    }

    @Override
    public void update(String statementId, Object... params) throws Exception {
        selectList(statementId, params);
    }

    @Override
    public void delete(String statementId, Object... params) throws Exception {
        selectList(statementId, params);
    }


    @Override
    public void close() throws SQLException {
        executor.close();
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        return (T) Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),
                new Class[]{mapperClass},
                new MapperProxy(this, configuration));
    }


}