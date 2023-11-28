package com.swe444.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@IdClass(RoleId.class)
public class Role {

    @Id
    @Column(name = "role")
    private String role;

    @Id
    @Column(name = "user_id")
    private String username;

    public Role() {
    }

    public Role(String role, String username) {
        this.role = role;
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
