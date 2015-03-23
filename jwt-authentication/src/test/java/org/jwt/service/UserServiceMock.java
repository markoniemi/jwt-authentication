package org.jwt.service;

import javax.inject.Named;

import org.jvnet.hk2.annotations.Service;

@Service
@Named
public class UserServiceMock implements UserService {

    @Override
    public Credentials findUser(String username) {
        if ("username".equals(username)) {
            return new Credentials(username, "password");
        }
        return null;
    }
}
