package com.qhj.service;

import com.qhj.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by QHJ on 2021/3/25
 */
public interface UserService {

    User checkUser(String username, String password);

    User checkAdmin(String username, String password);

    User getAdminUser();

    UserDetails loadUserByUsername(String username);

}
