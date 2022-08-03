package com.smartcontactmanager.smartcontactmanager.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontactmanager.smartcontactmanager.Dao.UserRepo;
import com.smartcontactmanager.smartcontactmanager.Entities.User;

public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user= userRepo.getUserByUserName(username);
        if(user==null)
        throw new UsernameNotFoundException("Could not found the user in the database");

        UserDetailImp userDetails=new UserDetailImp(user);
        return userDetails;
    }
    
}
