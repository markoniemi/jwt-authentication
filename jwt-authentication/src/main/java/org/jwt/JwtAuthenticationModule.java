package org.jwt;

import org.jwt.service.UserService;
import org.jwt.service.UserServiceImpl;

import com.google.inject.Binder;
import com.google.inject.Module;

public class JwtAuthenticationModule implements Module {
    public void configure(final Binder binder) {
        binder.bind(UserService.class).to(UserServiceImpl.class);
    }
}