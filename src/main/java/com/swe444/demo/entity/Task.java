package com.swe444.demo.entity;


import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "task")
public class Task {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id;

    @Column(name = "user_id")
    private String username;


    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private Boolean completed = false;

    @Column(name = "date")
    private String startDate;

    @Column(name = "edate")
    private String endDate;

    @Column(name = "category")
    private String category;


    public Task() {
    }

    public Task(String username, String title, Boolean completed, String startDate, String endDate) {
        this.username = username;
        this.title = title;
        this.completed = completed;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Task(String title) {
        this.title = title;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format(
                "Todo[id=%s, title='%s', completed='%s']",
                id, title, completed);
    }


}