package com.yunbao.sqlsession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 上午11:35
 */
public interface SqlSession {


    <T> List<T> selectList(String statementId, Object... params) throws Exception;


    <T> T selectOne(String statementId, Object... params) throws Exception;


    void insert(String statementId, Object... params) throws Exception;


    void update(String statementId, Object... params) throws Exception;


    void delete(String statementId, Object... params) throws Exception;


    void close() throws SQLException;


    <T> T getMapper(Class<T> mapperClass);


}
