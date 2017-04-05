package org.jwt.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import org.jwt.service.User;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTSigner.Options;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

/**
 * Utility class for handling JWT tokens.
 */
@Log4j2
public class JwtToken {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    protected static int expirySeconds = 600;
    protected String secret = "secret";
    protected static final String HEADER_PREFIX = "Bearer ";
    @Getter
    private String token;

    public JwtToken(User user) {
        this(user.asHashMap(), expirySeconds);
    }

    public JwtToken(Map<String, Object> payload) {
        this(payload, expirySeconds);
    }

    public JwtToken(Map<String, Object> payload, int expirySeconds) {
        JWTSigner jwtSigner = new JWTSigner(secret);
        Options options = new Options();
        options.setExpirySeconds(expirySeconds);
        options.setAlgorithm(Algorithm.HS512);
        token = jwtSigner.sign(payload, options);
    }

    public JwtToken(String token) {
        this.token = token;
    }

    public void verifyToken() throws JWTVerifyException  {
        try {
            new JWTVerifier(secret).verify(token);
        } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | SignatureException
                | IOException | JWTVerifyException e) {
            throw new JWTVerifyException(e.getMessage());
        }
    }

    /**
     * Accepts header with Bearer prefix and without it.
     */
    public static JwtToken parseToken(String header) {
        log.trace(header);
        if (header == null) {
            return null;
        }
        return new JwtToken(header.replace(HEADER_PREFIX, ""));
    }
}
