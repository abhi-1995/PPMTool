package com.fullstackbackend.ppmtool.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "User name must be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "please enter your full name")
    private String fullName;
    @NotBlank(message = "password field is required")
    private String password;
    @Transient
    private String confirmPassword;
    private Date create_At;
    private Date update_At;

    //OneToMany with Project

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }

}
