package org.jwt.service;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jvnet.hk2.annotations.Service;
import org.jwt.security.JwtToken;

@Slf4j
@Path("/")
@Service
public class LoginService {
    @Inject
    @Getter
    @Setter
    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public String login(Credentials credentials) throws AuthenticationException {
        Credentials user = userService.findUser(credentials.getUsername());
        if (user == null) {
            throw new AuthenticationException("Login error");
        }
        if (user.getPassword().equals(credentials.getPassword())) {
            log.debug("Username: {} logged in.", credentials.getUsername());
            return new JwtToken(credentials).getToken();
        } else {
            throw new AuthenticationException("Login error");
        }
    }

    @POST
    @Path("/logout")
    public void logout(@Context HttpServletRequest request) {
        String authenticationToken = (String) request.getHeader(JwtToken.AUTHORIZATION_HEADER);
        log.debug(authenticationToken);
    }
}
