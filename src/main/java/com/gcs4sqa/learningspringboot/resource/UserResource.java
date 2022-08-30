package com.gcs4sqa.learningspringboot.resource;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcs4sqa.learningspringboot.model.User;
import com.gcs4sqa.learningspringboot.service.UserService;

@RestController
@RequestMapping(
    path = "/api/V1/users"
)
public class UserResource {

    final UserService userService;


    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
        method = RequestMethod.GET
    )
    public List<User> fetchUsers(){
        return userService.getAllUsers();
        
    }


    @RequestMapping(
        method = RequestMethod.GET,
        path = "{userUid}"
    )
    public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid){
        return userService.getUser(userUid).<ResponseEntity<?>>map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessage("user " + userUid + " was not found")));
    }

    @RequestMapping(
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> inserNewUser(@RequestBody User user){
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);
        
    }


    @RequestMapping(
        method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> updateUser(@RequestBody User user){
        int result = userService.upDateUser(user);
        return getIntegerResponseEntity(result);

    }

    @RequestMapping(
        method = RequestMethod.DELETE,
        path = "{userUid}"
    )
    public ResponseEntity<Integer> deleteUser(@PathVariable("userUid") UUID userUid){
        int result = userService.removeUser(userUid);
        return getIntegerResponseEntity(result);

    }

    private ResponseEntity<Integer> getIntegerResponseEntity(int result){
        
        if (result == 1){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    
    }
    

class ErrorMessage{
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
