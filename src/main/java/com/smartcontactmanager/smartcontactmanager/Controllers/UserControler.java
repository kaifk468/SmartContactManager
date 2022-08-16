package com.smartcontactmanager.smartcontactmanager.Controllers;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    // open add contact form
    @GetMapping("/add_contact")
    public String addContact()
    {

        return "user/add_contact";
    }

    // TO proccesing form contact to add the contact to the databasse
    @PostMapping("/add_contact_db")
    public String addContactDb(@ModelAttribute("contact")Contact contact,Model model
                              ,Principal principal,@RequestParam("profilePic") MultipartFile file
                              ,HttpSession session)
                        
    {
        try{
            String userName=principal.getName();//it wiil return email as userName
            User user=userRepo.getUserByUserName(userName);
            contact.setUser(user);
           // user.getContacts().add(contact);
             
          
            if(file.isEmpty())
            {
                ///throw new Exception(new Messages("Something went wrong", "danger"));
                System.out.println("Somthing wint wrotn");
                contact.setImage("contact.png");
            }
            else{
                contact.setImage(file.getOriginalFilename());
                File saveFile=new ClassPathResource("static/images").getFile();
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("file uploded");

            }

            contactRepo.save(contact);
           // userRepo.save(user);
            model.addAttribute("contact", contact);
           
            return "user/add_contact";

        }
        catch(Exception e)
        {
            System.out.println("ERROR"+ e.getMessage());
            e.printStackTrace();
        
        }

        return "user/add_contact";
    }

    //showing all contacts with respet to user
    @GetMapping("show_contacts/{pageNo}")
    public String show_contacts(@PathVariable("pageNo") Integer pageNo,Principal principal,Model model)
    {
        User user=userRepo.getUserByUserName(principal.getName());
       
        Pageable paging = PageRequest.of(pageNo, 15);
        Page<Contact> pagedResult = contactRepo.getContactsByUserId(user.getId(),paging);
        
        model.addAttribute("contacts", pagedResult);
        model.addAttribute("currPage", pageNo);
        model.addAttribute("totalPage", pagedResult.getTotalPages());
        return "user/show_contacts";
        
    }

    ///showing single contact ddetail
    @GetMapping("contact/{id}")
    public String showContact(@PathVariable("id")Integer contactId,Model model)
    {
        Optional<Contact> optionalContact = contactRepo.findById(contactId);
        Contact contact=optionalContact.get();
        model.addAttribute("contact", contact);
        return "user/contact";
    }

    ///deleting user contact

    @GetMapping("/delete/{cid}")
    public String deletecContact(@PathVariable("cid")Integer contactId,Model model,HttpSession session)
    {

         Contact contact = contactRepo.findById(contactId).get();
         contactRepo.delete(contact);
         session.setAttribute("deleted", new Messages("Contacte Deleted", "success"));
         System.out.println("delete contatac succesfully");

        return "redirect:/user/show_contacts/0";
    }

    ///open new form to update user contact details
    @GetMapping("/update/{cid}")
    public String updateContactForm(@PathVariable("cid")Integer contactId,Model model)
    {
        Contact contact = contactRepo.findById(contactId).get();
        model.addAttribute("contact", contact);
        return "user/update_contact";
    }

    ///proccestin new updated contact details to the database
    @PostMapping("/update_contact_db/{cid}")
    public String updateContactDb(@PathVariable("cid")Integer contactId
                              ,@ModelAttribute("contact")Contact contact,Model model
                              ,Principal principal,@RequestParam("profilePic") MultipartFile file
                              ,HttpSession session)
                        
    {
        try{
            contact.setId(contactId);
            User userByUserName = userRepo.getUserByUserName(principal.getName());
            contact.setUser(userByUserName);
           // user.getContacts().add(contact);
             
          

            Contact oldContact = contactRepo.findById(contactId).get();
            if(file.isEmpty())
            {
                ///throw new Exception(new Messages("Something went wrong", "danger"));
                //System.out.println("Somthing wint wrotn");
                 
                 contact.setImage(oldContact.getImage());//setting old image as it is
            }
            else{

                contact.setImage(file.getOriginalFilename());//setting img name
                File saveFile=new ClassPathResource("static/images").getFile();//getting the image folder 
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());//finding the path of img
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);//coping the pic to tar folder
                System.out.println("file uploded");

                //deleting the old pic
                File file1=new File(saveFile,oldContact.getImage());
                file1.delete();

            }

            contactRepo.save(contact);
           // userRepo.save(user);
            model.addAttribute("contact", contact);
            System.out.println("contact updated");
            return "redirect:/user/show_contacts/0";

        }
        catch(Exception e)
        {
            System.out.println("ERROR"+ e.getMessage());
            e.printStackTrace();
        
        }

        return "redirect:/user/show_contacts/0";
    }

    ////user profile 
    @GetMapping("/profile")
    public String profile(Model model ,Principal principal)
    {
        User profile=userRepo.getUserByUserName(principal.getName());
        model.addAttribute("profile", profile);

        return "user/profile";
    }
    
}
