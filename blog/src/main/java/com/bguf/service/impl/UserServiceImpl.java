package com.bguf.service.impl;

import com.bguf.dao.UserRepository;
import com.bguf.entity.User;
import com.bguf.service.UserService;
import com.bguf.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author
 * @Description
 * @Date 7:26 下午 2020/10/10
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    /***
     * 通过用户昵称和密码查找数据库中的用户，校验登录的用户
     * @param name
     * @param password
     * @return
     */
    @Override
    @Transactional
    public User checkUser(String name, String password)
    {
        User user = userRepository.findUserByNicknameAndPassword(name, MD5Utils.code(password));
        return user;
    }

    /***
     * 保存用户，加密用户密码
     * @param user
     */
    @Override
    @Transactional
    public void register(User user)
    {
        user.setPassword(MD5Utils.code(user.getPassword()));
        userRepository.save(user);
    }

    /***
     * 用户注册校验
     * @param name
     * @return
     */
    @Override
    @Transactional
    public User checkRegister(String name)
    {
        User user = userRepository.findUserByNickname(name);
        return user;
    }
}
