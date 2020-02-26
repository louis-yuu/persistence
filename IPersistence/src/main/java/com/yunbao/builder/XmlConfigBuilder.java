package com.yunbao.builder;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yunbao.config.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by louisyuu on 2020/2/26 上午11:42
 * <p>
 * <p>
 * 解析XML配置文件
 * 并且初始化Configuration以及所有的MappedStatement（封装为JavaBean）
 * 在实际应用中 configuration在系统启动初始化一次即可
 */
public class XmlConfigBuilder    {


    protected Configuration configuration;


    public XmlConfigBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * @param inputStream
     * @return
     */
    public Configuration parseConfiguration(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);
        /**
         * <configuation> root element
         */
        Element rootElement = document.getRootElement();
        List<Element> propertyElements = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : propertyElements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("user"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
        xmlMapperBuilder.parseMappedStatement(rootElement);
        return configuration;
    }
}

