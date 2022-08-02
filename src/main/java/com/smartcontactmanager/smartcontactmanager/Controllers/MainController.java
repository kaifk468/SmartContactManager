package com.smartcontactmanager.smartcontactmanager.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartcontactmanager.smartcontactmanager.Dao.ContactRepo;
import com.smartcontactmanager.smartcontactmanager.Dao.UserRepo;
import com.smartcontactmanager.smartcontactmanager.Entities.Contact;
import com.smartcontactmanager.smartcontactmanager.Entities.User;
import com.smartcontactmanager.smartcontactmanager.Helper.Messages;

import ch.qos.logback.classic.sift.SiftAction;

@Controller
public class MainController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ContactRepo contactRepo;

    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("title","Home-SmartContactManager");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("title","About-SmartContactManager");
        return "about";
    }

    @GetMapping("/login")
    public String login(Model model)
    {
        model.addAttribute("title","Login-SmartContactManager");
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("title","Signup-SmartContactManager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_registration")
    public String regisration(@ModelAttribute("user") User user,
                              @RequestParam(value="repeat_password",defaultValue = "pass" ) String repeat_password,
                              @RequestParam(value="aggrement",defaultValue = "false" ) boolean aggrement,HttpSession session,Model model)
                              {
                                try{
                                    if(!aggrement)
                                    {
                                        throw new Exception("You have not agreed to the terms and conditions");

                                    }

                                    if(!user.getPassword().equals(repeat_password))
                                    {
                                        throw new Exception("Your password does not match");

                                    }
                                    user.setImageUrl("default.jpg");
                                    user.setRole("ROLE_USER");
                                    user.setStatus(true);


                                    User userResult=userRepo.save(user);
                                    model.addAttribute("user", new User());
                                    session.setAttribute("message",
                                              new Messages("Succesfully Registered! ", "alert-success"));
                                
                                    System.out.println("Aggrement "+ aggrement);
                                    System.out.println(user);
                                    return "signup";
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                    model.addAttribute("user",user);
                                    session.setAttribute("message",
                                              new Messages("Something went wrong ! "+e.getMessage(), "alert-danger"));
                                    return "signup";
                                }
                                
                              }
                              


















    @GetMapping("/test")
    @ResponseBody
    public String test()
    {
        System.out.println("In the testing mode---------------------------------------- ");
    
        Contact c1=new Contact("saif Siddique");
        Contact c2=new Contact("zaif Siddique");
        Contact c3=new Contact("taif Siddique");

        List<Contact> contacts=new ArrayList<>();
        contacts.add(c1);
        contacts.add(c2);
        contacts.add(c3);
        User user=new User("Ishrat", "isha@gmai.com", "8787uj8", "kk.com",
        "mother",true);
        
       /// Contact c3=new Contact();
        user.setContacts(contacts);
        userRepo.save(user);  
        //contactRepo.save(c1);   
        return "test is done";
    }
}
