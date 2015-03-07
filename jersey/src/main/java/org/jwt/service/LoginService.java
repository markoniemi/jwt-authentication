package org.jwt.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.jwt.security.JwtTokenUtil;

@Slf4j
@Path("/")
public class LoginService {
	private static final String AUTHORIZATION_HEADER = "Authorization";

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public String login(Credentials credentials,
			@Context HttpServletResponse response) throws IOException {
		if ("admin".equals(credentials.getUsername()) && "admin".equals(credentials.getPassword())){
			return JwtTokenUtil.createToken(credentials);
		}
		else {
			response.setHeader("WWW-Authenticate", "JWT");
			response.sendError(401, "Login error");
			return null;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public String login(@QueryParam("username") String username,
			@QueryParam("password") String password,
			@Context HttpServletResponse response) throws IOException {
		log.debug("username: {}, password: {}", username, password);
		return login(new Credentials(username, password), response);
	}

	@GET
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		String authenticationToken = (String) request
				.getHeader("Authorization");
		log.debug(authenticationToken);
	}

	@POST
	@Path("/logout")
	public void logoutWithGet(@Context HttpServletRequest request) {
		logout(request);
	}
}
