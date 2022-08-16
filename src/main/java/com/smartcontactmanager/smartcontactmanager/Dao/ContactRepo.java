package com.smartcontactmanager.smartcontactmanager.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import com.smartcontactmanager.smartcontactmanager.Entities.Contact;
import com.smartcontactmanager.smartcontactmanager.Entities.User;

@Component
public interface ContactRepo extends JpaRepository<Contact,Integer>{
    @Query("from Contact c where c.user.id=:userId")
    //pageable has two parameter 1.PageNo 2.PageSize
    public Page<Contact> getContactsByUserId(@Param("userId")Integer userId,Pageable pageable);

    //searching for contact which contain keyword and user
    public List<Contact> findByNameContainingAndUser(String keyword,User user);
}
