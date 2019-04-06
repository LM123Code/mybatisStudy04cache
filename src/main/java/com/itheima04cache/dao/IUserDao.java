package com.itheima04cache.dao;

import com.itheima04cache.domain.User;
import java.util.List;

/**
 * @author LM_Code
 * @create 2019-04-06-9:59
 */
public interface IUserDao {
    /**
     * 查寻所有用户
     * @return
     */
    public List<User> findAll();

    /**
     * 根据id查找用户
     * @return
     */
    public User findById(int id);
}
