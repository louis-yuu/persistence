package com.yunbao.config;

import com.yunbao.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 下午1:26
 */
public class BoundSql {

    private String sqlText;


    private List<ParameterMapping> parameterMappings = new ArrayList<>();


    public BoundSql(String sqlText, List<ParameterMapping> parameterMappings) {
        this.sqlText = sqlText;
        this.parameterMappings = parameterMappings;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }




}
