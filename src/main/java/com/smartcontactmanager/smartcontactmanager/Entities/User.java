package com.smartcontactmanager.smartcontactmanager.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String password;
    private String imageUrl;
    private String role;
    private boolean status;
    @OneToMany(cascade =CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Contact> contacts;


    public User() {
    }


    public User(String name, String email, String password, String imageUrl, String role, boolean status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.role = role;
        this.status = status;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public boolean isStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }


    public List<Contact> getContacts() {
        return contacts;
    }


    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }


    @Override
    public String toString() {
        return "User [contacts=" + contacts + ", email=" + email + ", id=" + id + ", imageUrl=" + imageUrl + ", name="
                + name + ", password=" + password + ", role=" + role + ", status=" + status + "]";
    }
     
    

    
}
