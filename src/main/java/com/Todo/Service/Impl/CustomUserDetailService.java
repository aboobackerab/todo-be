package com.Todo.Service.Impl;

import com.Todo.Entity.UserData;
import com.Todo.Repo.UserDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserDataRepo userDataRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData= userDataRepo.findByUsername(username).get();
        return new User(userData.getUsername(), userData.getPassword(),new ArrayList<>());
    }
}
