package com.qhj.service.impl;

import com.qhj.dao.UserRepository;
import com.qhj.pojo.User;
import com.qhj.service.UserService;
import com.qhj.utils.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by QHJ on 2021/3/25
 */
@Service
public class UserServiceImpl implements UserService , UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    /**
     * 验证用户登录信息
     * @param username
     * @param password
     * @return
     */
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, myPasswordEncoder.encode(password));
        return user;
    }

    @Override
    public User checkAdmin(String username, String password) {
        return userRepository.findByUsernameAndPasswordAndAdminIsTrue(username, myPasswordEncoder.encode(password));
    }

    @Override
    public User getAdminUser() {
        return userRepository.findUserByAdminIsTrue();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("不存在该用户!");
        }
        return user;
    }
}
