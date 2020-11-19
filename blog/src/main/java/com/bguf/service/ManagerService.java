package com.bguf.service;

import com.bguf.entity.Manager;
import org.springframework.stereotype.Service;

/**
 * @Author
 * @Description
 * @Date 1:52 下午 2020/10/5
 */
@Service
public interface ManagerService
{
    Manager checkManager(String name, String password);
    void register(Manager manager);
    Manager checkRegister(String name);
}

