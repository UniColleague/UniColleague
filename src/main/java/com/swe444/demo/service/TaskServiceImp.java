package com.swe444.demo.service;

import com.swe444.demo.dao.TaskDao;
import com.swe444.demo.dao.UserDao;
import com.swe444.demo.entity.Task;
import com.swe444.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements TaskService{

    EntityManager entityManager;
    TaskDao taskDao1;

    public TaskServiceImp(EntityManager entityManager, TaskDao taskDao){
        this.entityManager = entityManager;
        this.taskDao1 = taskDao;
    }

//    @Override
//    public List<Task> getAllTasks() {
////        TypedQuery<Task> query = entityManager.createQuery("select t from Task t where t.id in :data ORDER BY t.createdAt DESC", Task.class);
////        query.setParameter("data", );
////
////        List<> postList = query.getResultList();
//        return taskDao.findAllByOrderByCreatedAtDesc();
//    }

    @Override
    public Task getTaskById(int id) {
        Optional<Task> result = taskDao1.findById(id);

        Task task = null;
        if(result.isPresent()){
            task = result.get();
        }

        return task;
    }

    @Override
    public void saveTask(Task task) {
        task.setCompleted(false);
        taskDao1.save(task);
    }

    @Override
    @Transactional
    public void updateTask(Task task) {
        entityManager.merge(task);
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskDao1.delete(task);
    }
}
