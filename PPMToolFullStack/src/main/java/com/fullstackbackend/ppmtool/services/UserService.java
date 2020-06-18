package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.repositories.UserReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserReposiroty userReposiroty;

    
}
