package com.yunbao.config;

/**
 * Created by louisyuu on 2020/2/26 上午11:20
 *
 *
 * 该类用于存放从Mapper.xml解析出来的sql
 *
 *
 *
 */
public class MappedStatement {


    private String statementId;



    private String parameterType;


    private Class<?> parameterTypeClass;


    private String resultType;


    private Class<?> resultTypeClass;


    private String sql;


    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public Class<?> getParameterTypeClass() {
        return parameterTypeClass;
    }

    public void setParameterTypeClass(Class<?> parameterTypeClass) {
        this.parameterTypeClass = parameterTypeClass;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Class<?> getResultTypeClass() {
        return resultTypeClass;
    }

    public void setResultTypeClass(Class<?> resultTypeClass) {
        this.resultTypeClass = resultTypeClass;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    public String getSqlAsLowerCase() {
        return sql.toLowerCase();
    }
}
