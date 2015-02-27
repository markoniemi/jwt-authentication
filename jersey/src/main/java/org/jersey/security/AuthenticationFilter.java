//package org.jersey.security;
//
//import java.io.IOException;
//
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.Response.Status;
//
//import org.glassfish.jersey.server.ContainerRequest;
//
///**
// * Jersey HTTP Basic Auth filter
// * @author Deisss (LGPLv3)
// */
//public class AuthenticationFilter implements ContainerRequestFilter {
//    /**
//     * Apply the filter : check input request, validate or not with user auth
//     * @param containerRequest The request from Tomcat server
//     */
//    @Override
//	public void filter(ContainerRequestContext requestContext)
//			throws IOException {
//        //GET, POST, PUT, DELETE, ...
//        String method = requestContext.getMethod();
//        // myresource/get/56bCA for example
//        String path = requestContext.getPath(true);
// 
//        //We do allow wadl to be retrieve
//        if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd")){
//            return requestContext;
//        }
// 
//        //Get the authentification passed in HTTP headers parameters
//        String auth = requestContext.getHeaderValue("authorization");
// 
//        //If the user does not have the right (does not provide any HTTP Basic Auth)
//        if(auth == null){
//            throw new WebApplicationException(Status.UNAUTHORIZED);
//        }
// 
//        //lap : loginAndPassword
//        String[] lap = BasicAuth.decode(auth);
// 
//        //If login or password fail
//        if(lap == null || lap.length != 2){
//            throw new WebApplicationException(Status.UNAUTHORIZED);
//        }
// 
//        //DO YOUR DATABASE CHECK HERE (replace that line behind)...
//        User authentificationResult =  AuthentificationThirdParty.authentification(lap[0], lap[1]);
// 
//        //Our system refuse login and password
//        if(authentificationResult == null){
//            throw new WebApplicationException(Status.UNAUTHORIZED);
//        }
// 
//        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
// 
//        return requestContext;
//    }
//
//}