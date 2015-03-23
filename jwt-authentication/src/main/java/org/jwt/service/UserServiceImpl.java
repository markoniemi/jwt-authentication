package org.jwt.service;

import org.jvnet.hk2.annotations.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Credentials findUser(String username) {
        if ("admin".equals(username)) {
            return new Credentials(username, "admin");
        }
        return null;
    }
}
