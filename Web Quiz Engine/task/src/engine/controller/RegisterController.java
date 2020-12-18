package engine.controller;

import engine.entity.User;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/api/register")
    private ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
    }
}
