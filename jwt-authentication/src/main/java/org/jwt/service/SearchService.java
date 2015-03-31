package org.jwt.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/search")
public class SearchService {
    @Inject
    @Getter
    @Setter
    private UserService userService;
    @GET
    public List<User> echo(@PathParam("username") String username) {
        return userService.findAll();
    }

}
