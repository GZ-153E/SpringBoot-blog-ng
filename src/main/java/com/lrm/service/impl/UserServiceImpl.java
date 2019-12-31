package com.lrm.service.impl;

import com.lrm.dao.UserRepository;
import com.lrm.po.User;
import com.lrm.service.UserService;
import com.lrm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username, String passwrod) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(passwrod));
        return user;
    }
}
