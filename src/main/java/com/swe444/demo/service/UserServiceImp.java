package com.swe444.demo.service;


import com.swe444.demo.dao.UserDao;
import com.swe444.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImp implements UserService {

    EntityManager entityManager;
    UserDao userDao;

    public UserServiceImp(EntityManager entityManager, UserDao userDao){
        this.entityManager = entityManager;
        this.userDao = userDao;
    }


    @Override
    public User findUserByUsername(String name) {
        Optional<User> result = userDao.findById(name);

        User user = null;
        if(result.isPresent()){
            user = result.get();
        }

        return user;
    }

    @Override
    public List<User> searchForUserByUsername(String name) {
        String username = name.toLowerCase();

        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username like :data",User.class);
        query.setParameter("data", "%" + username + "%");

        List<User> users = query.getResultList();

        return users;
    }




    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    private Map<String, User> passwordResetTokens = new HashMap<>(); // Store tokens and associated users

    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email = :data", User.class);
        query.setParameter("data", email);

        User user = query.getSingleResult();
        return user;
    }

    public String createPasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        passwordResetTokens.put(token, user);
        return token;
    }

    public boolean isPasswordResetTokenValid(String token) {
        return passwordResetTokens.containsKey(token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        User user = passwordResetTokens.get(token);
        // Update user's password in the database with newPassword
        user.setPassword("{noop}" + newPassword);
        // Implement logic to update the user's password
        entityManager.merge(user);
        passwordResetTokens.remove(token); // Invalidate the token after use
    }
}
