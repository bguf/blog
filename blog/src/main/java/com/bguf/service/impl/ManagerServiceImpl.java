package com.bguf.service.impl;

import com.bguf.dao.ManagerRepository;
import com.bguf.entity.Manager;
import com.bguf.service.ManagerService;
import com.bguf.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author
 * @Description
 * @Date 1:52 下午 2020/10/5
 */
@Component
public class ManagerServiceImpl implements ManagerService
{
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    Manager manager;

    /***
     * 管理员注册成功后，使用加密方法设置密码，并保存管理员
     * @param manager
     */
    @Override
    @Transactional
    public void register(Manager manager)
    {
        manager.setCreateTime(new Date());
        manager.setPassword(MD5Utils.code(manager.getPassword()));
        managerRepository.save(manager);
    }

    /***
     * 用于校验管理员登录信息
     * @param name
     * @param password
     * @return
     */
    @Override
    @Transactional
    public Manager checkManager(String name, String password)
    {
        Manager manager = managerRepository.findManagerByManagernameAndPassword(name, MD5Utils.code(password));
        return manager;
    }

    /***
     * 通过管理员名字，判断是否已有管理员注册
     * @param name
     * @return
     */
    @Override
    @Transactional
    public Manager checkRegister(String name)
    {
        Manager manager = managerRepository.findManagerByManagername(name);
        return manager;
    }
}
