package com.swe444.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private String username;


    @Column(name = "pw")
    private String password;

    @Column(name = "active")
    private int active;


    @Column(name = "email")
    private String email;

    @Column(name = "image")
    private String image;


    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Task> tasks;





    public User(String username, String password, int active, String email) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.email = email;
    }

    public User(String username, String email, String about) {
        this.username = username;
        this.email = email;
    }

    public User(String email, String about) {
        this.email = email;
    }

    public User() {

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                '}';
    }

    public void addRole(Role role){

        if(roles == null){
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    public void addTask(Task task){

        if(tasks == null){
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }




}
