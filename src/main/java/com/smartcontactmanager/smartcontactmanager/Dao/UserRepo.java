package com.smartcontactmanager.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.smartcontactmanager.smartcontactmanager.Entities.Contact;
import com.smartcontactmanager.smartcontactmanager.Entities.User;

@Component
public interface UserRepo  extends JpaRepository<User,Integer>{
    
}
