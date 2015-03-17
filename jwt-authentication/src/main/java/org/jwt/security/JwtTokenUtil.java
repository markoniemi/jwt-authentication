package org.jwt.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.jwt.service.Credentials;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.JWTSigner.Options;

/**
 * Utility class for handling JWT tokens.
 */
@Slf4j
public class JwtTokenUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    protected static int expirySeconds = 60;
    protected static final String SECRET = "secret";
    protected static final String HEADER_PREFIX = "Bearer ";

    /**
     * Utility class has protected constructor
     */
    protected JwtTokenUtil() {
    }

    public static String createToken(Credentials credentials) {
        return createToken(credentials.asHashMap(), SECRET, expirySeconds);
    }

    public static String createToken(Map<String, Object> payload) {
        return createToken(payload, SECRET, expirySeconds);
    }

    public static String createToken(Map<String, Object> payload, String secret, int expirySeconds) {
        JWTSigner jwtSigner = new JWTSigner(secret);
        Options options = new Options();
        options.setExpirySeconds(expirySeconds);
        options.setAlgorithm(Algorithm.HS512);
        String token = jwtSigner.sign(payload, options);
        return token;
    }

    public static void verifyToken(String token, String secret) throws NoSuchAlgorithmException, InvalidKeyException,
            IOException, SignatureException, JWTVerifyException {
        new JWTVerifier(secret).verify(token);
    }

    /**
     * Accepts header with Bearer prefix and without it.
     */
    public static String parseToken(String header) {
        log.trace(header);
        if (header == null) {
            return null;
        }
        return header.replace(HEADER_PREFIX, "");
    }
}
