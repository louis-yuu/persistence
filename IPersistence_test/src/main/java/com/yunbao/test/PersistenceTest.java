package com.yunbao.test;

import com.yunbao.builder.SqlSessionFactoryBuilder;
import com.yunbao.io.Resources;
import com.yunbao.mapper.UserMapper;
import com.yunbao.pojo.User;
import com.yunbao.sqlsession.SqlSession;
import com.yunbao.sqlsession.SqlSessionFactory;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by louisyuu on 2020/2/26 上午11:16
 */
public class PersistenceTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void load() throws DocumentException, PropertyVetoException, ClassNotFoundException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

    }

    @Test
    public void testGetResourceAsStream() {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        System.out.println(resourceAsStream);
    }


    /**
     * 新增
     *
     * @throws Exception
     */
    @Test
    public void testInsert() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(4);
        user.setUsername("mysql");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.save(user);


    }


    /**
     * 修改
     *
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(4);
        user.setUsername("louis");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.update(user);
        sqlSession.close();

    }


    /**
     * 删除
     *
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteByPrimaryKey(4);

    }


    @Test
    public void testSelectOne() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimaryKey(2);
        System.out.println(user);

    }


    @Test
    public void testSelectAll() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        users.forEach(System.out::println);

    }


}
