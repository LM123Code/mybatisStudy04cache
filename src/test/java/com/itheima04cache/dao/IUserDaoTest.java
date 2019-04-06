package com.itheima04cache.dao;

import com.itheima04cache.domain.User;
import com.itheima04cache.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.List;

/**
 * IUserDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 6, 2019</pre>
 */
public class IUserDaoTest {
    SqlSession sqlSession = null;
    IUserDao userDao = null;
    @Before
    public void before() throws Exception {
        sqlSession = SqlSessionFactoryUtil.getSqlSession(true);
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void after() throws Exception {
        SqlSessionFactoryUtil.close(sqlSession);
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindById(){
        User user = userDao.findById(42);
        System.out.println(user);
    }

    /**
     * 一级缓存测试:基于SQL session的缓存
     * 当查询后，不调用SqlSession的commit/close和数据的增删改方法时，就不会清空缓存
     */
    @Test
    public void testFirstLevelCache(){
        User user1 = userDao.findById(42);
        System.out.println(user1);
        User user2 = userDao.findById(42);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    /**
     * 二级缓存：基于SQL session Factory的缓存（查询的类必须实现Serializable接口）
     * 1、让mybatis框架支持二级缓存（在SqlMapConfig.xml中配置）
     * 2、让当前的映射文件支持二级缓存（在IUserDao.xml中配置）
     * 3、让当前的操作支持二级缓存）（在select中配置）
     */
    @Test
    public void testSecondLevelCache(){
        SqlSession sqlSession1 = SqlSessionFactoryUtil.getSqlSession(true);
        IUserDao userDao1 = sqlSession1.getMapper(IUserDao.class);
        User user1 = userDao1.findById(41);
        sqlSession1.close();//关闭一级缓存

        SqlSession sqlSession2 = SqlSessionFactoryUtil.getSqlSession(true);
        IUserDao userDao2 = sqlSession2.getMapper(IUserDao.class);
        User user2 = userDao2.findById(41);
        sqlSession2.close();

        System.out.println(user1 == user2);//实现了二级缓存也是false
    }
} 
