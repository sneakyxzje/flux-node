package com.flux.node.domain.model;

import java.util.Set;

import com.flux.node.domain.enums.Role;

public class User {
    private Long id;
    private String email;
    private String username;
    private String fullname;
    private String password;    
    private Set<Role> roles;


    public User(Long id, String email, String username, String fullname, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFullname() {
        return this.fullname;
    }
    public void setFullname(String fullName) {
        this.fullname = fullName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
