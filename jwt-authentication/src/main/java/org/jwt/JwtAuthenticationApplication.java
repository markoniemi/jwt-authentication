package org.jwt;

import org.glassfish.jersey.server.ResourceConfig;

public class JwtAuthenticationApplication extends ResourceConfig {
    public JwtAuthenticationApplication() {
        register(new DependencyBinder());
        packages(true, "org.jwt.service");
    }
}
