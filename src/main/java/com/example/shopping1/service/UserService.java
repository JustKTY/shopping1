package com.example.shopping1.service;

import com.example.shopping1.entities.User;
import com.example.shopping1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 注册功能
     * @param user
     * @return
     */
    public boolean addUser(User user) {
        // 1、根据用户名查询用户对象
        User u = userMapper.findByUsername(user.getUserName());
        if(u!=null){
            //用户名存在，注册失败
            return false;
        }
        // 2、保存用户信息
        userMapper.addUser(user);
        return true;
    }

    /**
     * 登录功能
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        return userMapper.findByUnameAndPsd(username,password);
    }
}
