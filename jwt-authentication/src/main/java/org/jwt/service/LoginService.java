package org.jwt.service;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.jwt.security.JwtTokenUtil;

@Slf4j
@Path("/")
public class LoginService {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public String login(Credentials credentials) throws AuthenticationException {
        if ("admin".equals(credentials.getUsername()) && "admin".equals(credentials.getPassword())) {
            log.debug("Username: {} logged in.", credentials.getUsername());
            return JwtTokenUtil.createToken(credentials);
        } else {
            throw new AuthenticationException("Login error");
        }
    }

    @POST
    @Path("/logout")
    public void logout(@Context HttpServletRequest request) {
        String authenticationToken = (String) request.getHeader(JwtTokenUtil.AUTHORIZATION_HEADER);
        log.debug(authenticationToken);
    }
}
