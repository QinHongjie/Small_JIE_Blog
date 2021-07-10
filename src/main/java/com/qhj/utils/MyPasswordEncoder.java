package com.qhj.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * 自定义的密码加密方法，实现了PasswordEncoder接口
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    /**
     * md5加密方法
     * @param charSequence
     * @return
     */
    @Override
    public String encode(CharSequence charSequence) {
        try {
            // MD5加密密码
            return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密比对
     * @param charSequence
     * @param s
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equals(s);
    }

}
