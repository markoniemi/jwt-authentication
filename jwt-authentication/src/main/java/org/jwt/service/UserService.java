package org.jwt.service;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface UserService {
    public Credentials findUser(String username);
}
