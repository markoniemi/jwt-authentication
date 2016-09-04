package org.jwt.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

@Named("UserServiceMock")
public class UserServiceMock implements UserService {
    @Override
    public List<User> findAll() {
        List<User> user = new ArrayList<User>();
        user.add(new User("admin", "admin"));
        user.add(new User("user", "user"));
        return user;
    }

    @Override
    public User findUser(String username) {
        if ("username".equals(username)) {
            return new User(username, "password");
        }
        return null;
    }
}
