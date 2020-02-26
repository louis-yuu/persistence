package com.yunbao.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by louisyuu on 2020/2/26 上午11:18
 * <p>
 * 数据库配置信息等配置
 */
public class Configuration {


    private DataSource dataSource;


    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
