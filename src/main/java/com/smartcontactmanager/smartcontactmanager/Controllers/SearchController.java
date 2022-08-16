package com.smartcontactmanager.smartcontactmanager.Controllers;

import java.security.Principal;
import java.util.List;

import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontactmanager.smartcontactmanager.Dao.ContactRepo;
import com.smartcontactmanager.smartcontactmanager.Dao.UserRepo;
import com.smartcontactmanager.smartcontactmanager.Entities.Contact;
import com.smartcontactmanager.smartcontactmanager.Entities.User;

@RestController
public class SearchController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    ContactRepo contactRepo;


    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query")String query,Principal principal)
    {
        User user=userRepo.getUserByUserName(principal.getName());
        System.out.println(query+"///////////////////////////");
        List<Contact> contacts = contactRepo.findByNameContainingAndUser(query, user);
        
        return ResponseEntity.ok(contacts);
    }
    
}
