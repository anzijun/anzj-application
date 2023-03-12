package com.azj.anzj.service;

import com.azj.anzj.mapper.UserMapper;
import com.azj.anzj.pojo.User;
import com.azj.anzj.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {

        User user = userMapper.findByUsernameAndPassword(username,Md5Util.code(password));
        return user;
    }
}
