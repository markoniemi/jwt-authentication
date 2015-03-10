package org.jwt.security;

import java.security.SignatureException;

import org.junit.Assert;
import org.junit.Test;
import org.jwt.service.Credentials;

import com.auth0.jwt.JWTExpiredException;

public class JwtTokenUtilTest {
	@Test
	public void parseToken() {
		String header = "Bearer <token>";
		Assert.assertEquals("<token>", JwtTokenUtil.parseToken(header));
	}

	@Test
	public void parseTokenWithoutPrefix() {
		String header = "<token>";
		Assert.assertEquals("<token>", JwtTokenUtil.parseToken(header));
	}

	@Test
	public void verifyToken() {
		try {
			Credentials credentials = new Credentials("username", "password");
			String token = JwtTokenUtil.createToken(credentials.asHashMap(), "secret", 10);
			JwtTokenUtil.verifyToken(token, "secret");
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test()
	public void verifyTokenWithExpiredToken() {
		try {
			Credentials credentials = new Credentials("username", "password");
			String token = JwtTokenUtil.createToken(credentials.asHashMap(), "secret", 1);
			Thread.sleep(1000);
			JwtTokenUtil.verifyToken(token, "secret");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(e instanceof JWTExpiredException);
		}
	}
	@Test()
	public void verifyTokenWithInvalidSignature() {
		try {
			Credentials credentials = new Credentials("username", "password");
			String token = JwtTokenUtil.createToken(credentials.asHashMap(), "secret", 10);
			JwtTokenUtil.verifyToken(token, "invalid");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(e instanceof SignatureException);
		}
	}
}
