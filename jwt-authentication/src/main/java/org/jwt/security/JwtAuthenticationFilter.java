package org.jwt.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.regex.Pattern;

import javax.naming.AuthenticationException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

@Slf4j
public class JwtAuthenticationFilter implements Filter {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private String secret;
	private String loginUrl;

	@Override
	// TODO add logging
	public void init(FilterConfig filterConfig) throws ServletException {
		secret = filterConfig.getInitParameter("secret");
		loginUrl = filterConfig.getInitParameter("loginUrl");
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (isLoginUrl(request)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			String token = getToken(request);
			if (token != null) {
				try {
					JwtTokenUtil.verifyToken(token, secret);
					filterChain.doFilter(servletRequest, servletResponse);
					// TODO show different message for different errors
				} catch (InvalidKeyException | NoSuchAlgorithmException
						| IllegalStateException | SignatureException
						| JWTVerifyException e) {
					unauthorized(response);
				}
			} else {
				unauthorized(response);
			}
		}
	}

	/**
	 * Token is in request header: Authorization : Bearer <token>. Accepts header with Bearer prefix and without it.
	 * @return null if token was not found.
	 */
	protected String getToken(HttpServletRequest request) {
		return JwtTokenUtil.parseToken(request.getHeader(AUTHORIZATION_HEADER));
	}

	private boolean isLoginUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		log.debug(uri);
		return uri.equals(contextPath + loginUrl);
	}

	@Override
	public void destroy() {
	}

	private void unauthorized(HttpServletResponse response, String message)
			throws IOException {
		response.setHeader("WWW-Authenticate", "JWT");
		response.sendError(401, message);
	}

	private void unauthorized(HttpServletResponse response) throws IOException {
		unauthorized(response, "Unauthorized");
	}
}