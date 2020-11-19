package com.bguf.dao;

import com.bguf.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 有关管理员的底层操作
 * @Author
 * @Description
 * @Date 1:54 下午 2020/10/5
 */
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>
{
    //根据管理员真实姓名和管理员密码，查询所有的管理员
    Manager findManagerByManagernameAndPassword(String name, String password);
    //根据管理员的真实姓名，查询管理员
    Manager findManagerByManagername(String name);
}
