package com.yunbao.builder;

import com.yunbao.builder.XmlConfigBuilder;
import com.yunbao.config.Configuration;
import com.yunbao.sqlsession.DefaultSqlSessionFactory;
import com.yunbao.sqlsession.SqlSessionFactory;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * Created by louisyuu on 2020/2/26 上午11:34
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;


    public SqlSessionFactoryBuilder() {
        this.configuration=new Configuration();
    }

    public SqlSessionFactory build(InputStream is) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        //构建Configuration或者说build Configuration
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(configuration);
        //解析xml 封装Configuration
        xmlConfigBuilder.parseConfiguration(is);
        return new DefaultSqlSessionFactory(configuration);
    }
}
