package com.gcs4sqa.learningspringboot.resource;

import com.gcs4sqa.learningspringboot.model.User;
import com.gcs4sqa.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("api/V1/users")
public class UserResourceResteasy {

    private UserService userService;

    @Autowired
    public UserResourceResteasy(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        return userService.getAllUsers(Optional.ofNullable(gender));

    }


    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")
    public Response fetchUser(@PathParam("userUid") UUID userUid) {
        Optional<User> userOptional = userService.getUser(userUid);
        if (userOptional.isPresent()){
            return Response.ok(userOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("user " + userUid + " was not found"))
                .build();
         }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response inserNewUser(@RequestBody User user) {
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);

    }



    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updateUser(@RequestBody User user) {
        int result = userService.upDateUser(user);
        return getIntegerResponseEntity(result);

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{userUid}"
    )
    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")
    public Response deleteUser(@PathParam("userUid") UUID userUid) {
        int result = userService.removeUser(userUid);
        return getIntegerResponseEntity(result);

    }

    private Response getIntegerResponseEntity(int result) {

        if (result == 1) {
            return Response.ok().build();
        }
        return Response.serverError().build();



    }

    class ErrorMessage {
        String errorMessage;


        public ErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

    }
}
