package com.smartcontactmanager.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.smartcontactmanager.smartcontactmanager.Entities.Contact;

@Component
public interface ContactRepo extends JpaRepository<Contact,Integer>{
    
}
