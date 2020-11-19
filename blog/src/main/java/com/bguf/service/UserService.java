package com.bguf.service;

import com.bguf.entity.Manager;
import com.bguf.entity.User;

/**
 * @Author
 * @Description
 * @Date 7:26 下午 2020/10/10
 */
public interface UserService
{
    User checkUser(String name, String password);
    void register(User user);
    User checkRegister(String name);
}
