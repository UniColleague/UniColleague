package com.swe444.demo.service;

import com.swe444.demo.entity.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    //public List<Task> getAllTasks();

    public Task getTaskById(int id);

    public void saveTask(Task task);

    public void updateTask(Task task);

    public void deleteTask(Task task);

    public List<Task> getAllTasks();
}
