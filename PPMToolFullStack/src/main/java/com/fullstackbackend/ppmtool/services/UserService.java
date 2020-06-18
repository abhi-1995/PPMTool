package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.domain.User;
import com.fullstackbackend.ppmtool.repositories.UserReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserReposiroty userReposiroty;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userReposiroty.save(newUser);
    }

}