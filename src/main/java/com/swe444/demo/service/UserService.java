package com.swe444.demo.service;



import com.swe444.demo.entity.User;

import java.util.List;

public interface UserService {


    User findUserByUsername(String name);

    List<User> searchForUserByUsername(String name);

    User findByEmail(String email);

    String createPasswordResetToken(User user);

    boolean isPasswordResetTokenValid(String token);

    void resetPassword(String token, String newPassword);

    List<User> findAllUsers();

    void save(User user);

    void update(User user);
}
