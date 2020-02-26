package com.yunbao.sqlsession;

import com.yunbao.config.Configuration;

/**
 * Created by louisyuu on 2020/2/26 下午12:58
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {


    private Configuration configuration;


    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
