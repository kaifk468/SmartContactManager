package com.smartcontactmanager.smartcontactmanager.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontactmanager.smartcontactmanager.Dao.UserRepo;
import com.smartcontactmanager.smartcontactmanager.Entities.User;

@Controller
@RequestMapping("/user")
public class UserControler {

    @Autowired
    UserRepo userRepo;

    @RequestMapping("/index")
    public String userIndex(Model model,Principal principal)
    {

        String userName=principal.getName();
        User user=userRepo.getUserByUserName(userName);

        model.addAttribute("user", user);
        return "user/index";


    }

    //public String 
    
}
