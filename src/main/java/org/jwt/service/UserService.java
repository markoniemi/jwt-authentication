package org.jwt.service;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUser(String username);
}
