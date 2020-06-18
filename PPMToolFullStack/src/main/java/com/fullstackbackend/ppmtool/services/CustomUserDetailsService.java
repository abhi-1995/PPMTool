package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.domain.User;
import com.fullstackbackend.ppmtool.repositories.UserReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserReposiroty userReposiroty;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReposiroty.findByUsername(username);
        if(user==null) new UsernameNotFoundException("User not found");
        return user;
    }

    @Transactional
    public User loadUserById(Long id){
        User user = userReposiroty.getById(id);
        if(user==null) new UsernameNotFoundException("User not found");
        return user;
    }
}
