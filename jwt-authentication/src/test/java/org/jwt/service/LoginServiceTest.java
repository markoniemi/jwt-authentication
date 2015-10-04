package org.jwt.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.AuthenticationException;

import org.junit.Assert;
import org.junit.Test;

public class LoginServiceTest  {
    @Inject 
    LoginService loginService;
    @Inject @Named("UserServiceMock")
    UserService userService;
    
    @Test
    public void login() throws AuthenticationException {
        loginService.setUserService(userService);
        String token = loginService.login(new User("username", "password"));
        Assert.assertNotNull(token);
    }
}
