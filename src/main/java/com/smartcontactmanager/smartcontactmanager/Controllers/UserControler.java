package com.smartcontactmanager.smartcontactmanager.Controllers;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.smartcontactmanager.Dao.ContactRepo;
import com.smartcontactmanager.smartcontactmanager.Dao.UserRepo;
import com.smartcontactmanager.smartcontactmanager.Entities.Contact;
import com.smartcontactmanager.smartcontactmanager.Entities.User;
import com.smartcontactmanager.smartcontactmanager.Helper.Messages;

@Controller
@RequestMapping("/user")
public class UserControler {

    @Autowired
    UserRepo userRepo;
    @Autowired
    ContactRepo contactRepo;

    @RequestMapping("/index")
    public String userIndex(Model model,Principal principal)
    {

        String userName=principal.getName();
        User user=userRepo.getUserByUserName(userName);

        model.addAttribute("user", user);
        return "user/index";


    }

    @GetMapping("/add_contact")
    public String addContact()
    {

        return "user/add_contact";
    }

    // proccesing form contact
    @PostMapping("/add_contact_db")
    public String addContactDb(@ModelAttribute("contact")Contact contact,Model model
                              ,Principal principal,@RequestParam("profilePic") MultipartFile file
                              ,HttpSession session)
                        
    {
        try{
            String userName=principal.getName();//it wiil return email as userName
            User user=userRepo.getUserByUserName(userName);
            contact.setUser(user);
            user.getContacts().add(contact);
            // contactRepo.save(contact);
          
            if(file.isEmpty())
            {
                ///throw new Exception(new Messages("Something went wrong", "danger"));
                System.out.println("Somthing wint wrotn");
            }
            else{
                contact.setImage(file.getOriginalFilename());
                File saveFile=new ClassPathResource("static/images").getFile();
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("file uploded");

            }


            userRepo.save(user);
            model.addAttribute("contact", contact);
            //System.out.println(contact);
            return "user/add_contact";

        }
        catch(Exception e)
        {
            System.out.println("ERROR"+ e.getMessage());
            e.printStackTrace();
        
        }

        return "user/add_contact";
    }

    //public String 
    
}
