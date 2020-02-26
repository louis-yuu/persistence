package com.yunbao.builder;

import com.yunbao.config.Configuration;
import com.yunbao.config.MappedStatement;
import com.yunbao.io.Resources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 下午12:23
 */
public class XmlMapperBuilder {

    protected Configuration configuration;


    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     *
     * @param rootElement <configuation>
     * @throws DocumentException
     * @throws ClassNotFoundException
     */

    public void parseMappedStatement(Element rootElement) throws DocumentException, ClassNotFoundException {
        List<Element> mapperElements = rootElement.selectNodes("//mapper");
        for (Element element : mapperElements) {
            String value = element.attributeValue("resource");
            InputStream mappersInputStream = Resources.getResourceAsStream(value);
            doParseMappedStatement(mappersInputStream);
        }
    }


    /**
     * 解析每个mapper.xml 里面的xml标签
     *
     * @param inputStream
     * @throws DocumentException
     */
    protected void doParseMappedStatement(InputStream inputStream) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);
        /**
         * <mapper></mapper> root element
         */
        Element rootElement = document.getRootElement();
        //解析select
        parseSelectElements(rootElement);
        //解析insert
        parseInsertElements(rootElement);
        //解析update
        parseUpdateElements(rootElement);
        //解析delete
        parseDeleteElements(rootElement);

    }


    /**
     * 解析select标签
     *
     * @param rootElement rootElement
     */
    protected void parseSelectElements(Element rootElement) throws ClassNotFoundException {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selects = rootElement.selectNodes("select");
        parseElements(selects, namespace);
    }


    /**
     * 解析insert标签
     *
     * @param rootElement rootElement
     */
    protected void parseInsertElements(Element rootElement) throws ClassNotFoundException {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> inserts = rootElement.selectNodes("insert");
        parseElements(inserts, namespace);
    }


    /**
     * 解析update标签
     *
     * @param rootElement rootElement
     */
    protected void parseUpdateElements(Element rootElement) throws ClassNotFoundException {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> updates = rootElement.selectNodes("update");
        parseElements(updates, namespace);
    }


    /**
     * 解析delete标签
     *
     * @param rootElement rootElement
     */
    protected void parseDeleteElements(Element rootElement) throws ClassNotFoundException {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> deletes = rootElement.selectNodes("delete");
        parseElements(deletes, namespace);
    }


    /**
     * @param elements 具体的标签集合 eg.select insert update delete
     * @throws ClassNotFoundException
     */
    protected void parseElements(List<Element> elements, String namespace) throws ClassNotFoundException {
        for (Element el : elements) {
            String id = el.attributeValue("id");
            String parameterType = el.attributeValue("parameterType");
            String resultType = el.attributeValue("resultType");
            String sql = el.getTextTrim();
            MappedStatement ms = new MappedStatement();
            ms.setStatementId(namespace + "." + id);
            ms.setParameterType(parameterType);
            ms.setResultType(resultType);
            ms.setSql(sql);
            ms.setParameterTypeClass(getClassType(parameterType));
            ms.setResultTypeClass(getClassType(resultType));
            configuration.getMappedStatementMap().put(ms.getStatementId(), ms);
        }
    }


    private Class<?> getClassType(String className) throws ClassNotFoundException {
        if (className == null || "".equals(className.trim())) {
            return null;
        }
        return Class.forName(className);


    }


}
