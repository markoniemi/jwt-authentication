package org.jwt.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.AuthenticationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class LoginServiceTest {
    @Inject
    LoginService loginService;
    @Inject
    @Named("UserServiceMock")
    UserService userService;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new LoginServiceTestModule());
        loginService = injector.getInstance(LoginService.class);
    }

    @Test
    public void login() throws AuthenticationException {
        String token = loginService.login(new User("username", "password"));
        Assert.assertNotNull(token);
    }

    public class LoginServiceTestModule implements Module {
        public void configure(final Binder binder) {
            binder.bind(UserService.class).to(UserServiceMock.class);
            binder.bind(LoginService.class);
            binder.bind(EchoService.class);
            binder.bind(SearchService.class);
        }
    }
}
