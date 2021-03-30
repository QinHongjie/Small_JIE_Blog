package com.qhj.core;

import com.qhj.pojo.User;
import com.qhj.service.UserService;
import com.qhj.utils.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LoginValidateAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取输入的用户名
        String username = authentication.getName();
        //获取输入的明文
        String rawPassword = (String) authentication.getCredentials();

        //查询用户是否存在
        User user = (User) userService.loadUserByUsername(username);

        if (!user.isEnabled()) {
            throw new DisabledException("该账户已被禁用，请联系管理员");

        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定");

        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期，请联系管理员");

        } else if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录");
        }

        // 验证是否管理员
        if( !user.isAdmin()) {
            throw  new BadCredentialsException("该账户没有管理权限！");
        }

        //验证密码
        if (!myPasswordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("输入密码错误!");
        }

        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(user, rawPassword, user.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        //确保authentication能转成该类
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
