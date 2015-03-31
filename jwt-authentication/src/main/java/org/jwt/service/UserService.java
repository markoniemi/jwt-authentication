package org.jwt.service;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface UserService {
    List<User> findAll();

    User findUser(String username);
}
