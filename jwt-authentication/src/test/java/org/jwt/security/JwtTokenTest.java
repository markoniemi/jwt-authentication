package org.jwt.security;

import java.security.SignatureException;

import org.junit.Assert;
import org.junit.Test;
import org.jwt.service.Credentials;

import com.auth0.jwt.JWTExpiredException;
import com.auth0.jwt.JWTSigner;

public class JwtTokenTest {
    @Test
    public void parseToken() {
        String header = "Bearer <token>";
        Assert.assertEquals("<token>", JwtToken.parseToken(header).getToken());
    }

    @Test
    public void parseTokenWithoutPrefix() {
        String header = "<token>";
        Assert.assertEquals("<token>", JwtToken.parseToken(header).getToken());
    }

    @Test
    public void verifyToken() {
        try {
            Credentials credentials = new Credentials("username", "password");
            JwtToken token = new JwtToken(credentials.asHashMap(), 10);
            token.verifyToken();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void verifyTokenWithExpiredToken() {
        try {
            Credentials credentials = new Credentials("username", "password");
            JwtToken token = new JwtToken(credentials.asHashMap(), 1);
            Thread.sleep(1000);
            token.verifyToken();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof JWTExpiredException);
        }
    }

    @Test()
    public void verifyTokenWithInvalidSignature() {
        try {
            Credentials credentials = new Credentials("username", "password");
            JWTSigner jwtSigner = new JWTSigner("wrong_secret");
            String tokenString = jwtSigner.sign(credentials.asHashMap());
            JwtToken token = new JwtToken(tokenString);
            token.verifyToken();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof SignatureException);
        }
    }
}
