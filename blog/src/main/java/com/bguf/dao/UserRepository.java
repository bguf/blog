package com.bguf.dao;

import com.bguf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 关于用户的底层操作
 * @Author
 * @Description
 * @Date 7:33 下午 2020/10/10
 */
public interface UserRepository extends JpaRepository<User, Long>
{
    //通过用户昵称和密码查找所有用户
    User findUserByNicknameAndPassword(String name, String password);
    //通过用户昵称查找所有用户
    User findUserByNickname(String name);
}
