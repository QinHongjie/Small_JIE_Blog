package com.qhj.dao;

import com.qhj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by QHJ on 2021/3/25
 * 操作数据库
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String userName, String password);

    User findByUsernameAndPasswordAndAdminIsTrue(String userName, String password);

    User findUserByAdminIsTrue();

    User findUserByUsername(String username);

}
