package com.exam.service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    //creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    public User getUser(String username);

    //delete by user Id
    public void deleteUser(long userId);

    //public User findUserById(Long userId, User user);
}
