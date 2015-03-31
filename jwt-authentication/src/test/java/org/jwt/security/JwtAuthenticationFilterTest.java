package org.jwt.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jwt.service.User;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    FilterConfig filterConfig;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doFilterWithLoginUrl() throws ServletException, IOException {
        String contextPath = "/jwt-authentication";
        String loginUrl = "/api/rest/login";
        String requestUri=contextPath+loginUrl;
        prepareMock(loginUrl, contextPath, requestUri, null);
        doFilter();
        Mockito.verify(filterChain).doFilter(request, response);
    }
    @Test
    public void doFilterWithNoHeader() throws ServletException, IOException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        prepareMock(loginUrl, contextPath, requestUri, null);
        doFilter();
        Mockito.verify(response).sendError(401, "Unauthorized");
    }
    @Test
    public void doFilterWithValidToken() throws ServletException, IOException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        prepareMock(loginUrl, contextPath, requestUri, new JwtToken(new User("username", "password")).getToken());
        doFilter();
        Mockito.verify(filterChain).doFilter(request, response);
    }
    @Test
    public void doFilterWithBearerAndValidToken() throws ServletException, IOException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        prepareMock(loginUrl, contextPath, requestUri, "Bearer " + new JwtToken(new User("username", "password")).getToken());
        doFilter();
        Mockito.verify(filterChain).doFilter(request, response);
    }
    @Test
    public void doFilterWithExpiredToken() throws ServletException, IOException, InterruptedException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        JwtToken.expirySeconds=1;
        prepareMock(loginUrl, contextPath, requestUri, new JwtToken(new User("username", "password")).getToken());
        Thread.sleep(1000);
        doFilter();
        Mockito.verify(response).sendError(401, "Unauthorized");
    }

    private void doFilter() throws ServletException, IOException {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.init(filterConfig);
        jwtAuthenticationFilter.doFilter(request, response, filterChain);
    }

    private void prepareMock(String loginUrl, String contextPath, String requestUri, String token) {
        Mockito.when(filterConfig.getInitParameter("loginUrl")).thenReturn(loginUrl);
        Mockito.when(request.getContextPath()).thenReturn(contextPath);
        Mockito.when(request.getRequestURI()).thenReturn(requestUri);
        Mockito.when(request.getHeader(JwtToken.AUTHORIZATION_HEADER)).thenReturn(token);
    }
}
