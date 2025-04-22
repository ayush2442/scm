package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entites.User;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    //If need then add more methods related to user service logic
    
}
