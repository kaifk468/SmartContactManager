package com.smartcontactmanager.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.smartcontactmanager.smartcontactmanager.Entities.Contact;
import com.smartcontactmanager.smartcontactmanager.Entities.User;

@Component
public interface UserRepo  extends JpaRepository<User,Integer>{

    /**
     * @param email
     * @return
     */
    @Query("select u from User u where u.email=:email")
    public User getUserByUserName(@Param("email")String email);
    
}
