package com.yunbao.excutor;

import com.yunbao.config.Configuration;
import com.yunbao.config.MappedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 下午1:03
 */
public interface Executor {

    <T> List<T> execute(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;


    void close() throws SQLException;

}
